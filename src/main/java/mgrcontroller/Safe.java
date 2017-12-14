package mgrcontroller;

import dao.SafeDao;
import gnu.io.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * 串口读取类
 */
public class Safe implements Runnable, SerialPortEventListener {

    private static Safe serial = null;

    //获取实例
    public static synchronized Safe getInstance(String comPort) throws Exception {
        if (serial == null) {
            serial = new Safe();
            serial.selectPort(comPort);
            //开始监听从端口中接收的数据,单位是秒，如果是0就一直监听
            serial.startRead(3600);
            return serial;
        } else{
            return serial;
        }
    }

    private String appName = "接收串信息";
    private int timeout = 2000;//open 端口时的等待时间
    private int threadTime = 0;

    private CommPortIdentifier commPort;
    private SerialPort serialPort;
    private InputStream inputStream;
    static String state;// 状态
    static String temperature;// 温度
    static String heartrate;// 心率
    static String longitude;// 声音
    static String latitude;// 人体
    static Date updateDate ;//时间

    /**
     * @方法名称 :listPort
     * @功能描述 :列出所有可用的串口
     * @返回值类型 :void
     */
    @SuppressWarnings("rawtypes")
    public void listPort(){
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        System.out.println("now to list all Port of this PC：" +en);

        while(en.hasMoreElements()){
            cpid = (CommPortIdentifier)en.nextElement();
            if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL){
                System.out.println(cpid.getName() + ", " + cpid.getCurrentOwner());
            }
        }
    }


    /**
     * @方法名称 :selectPort
     * @功能描述 :选择一个端口，比如：COM1
     * @返回值类型 :void
     *  @param portName
     * @throws UnsupportedCommOperationException
     */
    @SuppressWarnings("rawtypes")
    public void selectPort(String portName) throws UnsupportedCommOperationException{

        this.commPort = null;
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        while(en.hasMoreElements()){
            cpid = (CommPortIdentifier)en.nextElement();
            if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL
                    && cpid.getName().equals(portName)){
                this.commPort = cpid;
                break;
            }
        }
        openPort();
    }

    /**
     * @throws UnsupportedCommOperationException
     * @方法名称 :openPort
     * @功能描述 :打开SerialPort
     * @返回值类型 :void
     */
    private void openPort() throws UnsupportedCommOperationException{
        if(commPort == null)
            log(String.format("无法找到名字为'%1$s'的串口！", commPort.getName()));
        else{
            log("端口选择成功，当前端口："+commPort.getName()+",现在实例化 SerialPort:");

            try{
                serialPort = (SerialPort)commPort.open(appName, timeout);
                //端口初始化
                serialPort.setSerialPortParams(115200, 8, 1, 0);

                log("实例 SerialPort 成功！");
            }catch(PortInUseException e){
                throw new RuntimeException(String.format("端口'%1$s'正在使用中！",
                        commPort.getName()));
            }
        }
    }

    /**
     * @方法名称 :checkPort
     * @功能描述 :检查端口是否正确连接
     * @返回值类型 :void
     */
    private void checkPort(){
        if(commPort == null)
            throw new RuntimeException("没有选择端口，请使用 " +
                    "selectPort(String portName) 方法选择端口");
        if(serialPort == null){
            throw new RuntimeException("SerialPort 对象无效！");
        }
    }


    /**
     * @方法名称 :startRead
     * @功能描述 :开始监听从端口中接收的数据
     * @返回值类型 :void
     *  @param time  监听程序的存活时间，单位为秒，0 则是一直监听
     */
    public void startRead(int time){
        checkPort();
        try{
            inputStream = new BufferedInputStream(serialPort.getInputStream());
        }catch(IOException e){
            throw new RuntimeException("获取端口的InputStream出错："+e.getMessage());
        }

        try{
            serialPort.addEventListener(this);
        }catch(TooManyListenersException e){
            throw new RuntimeException(e.getMessage());
        }

        serialPort.notifyOnDataAvailable(true);

        log(String.format("开始监听来自'%1$s'的数据--------------", commPort.getName()));
        if(time > 0){
            this.threadTime = time*1000;
            Thread t = new Thread(this);
            t.start();
            log(String.format("监听程序将在%1$d秒后关闭。。。。", threadTime));
        }
    }

    /**
     * 数据接收的监听处理函数
     * 监听事件，启动后该事件会读取对应串口信息
     */

    public void serialEvent(SerialPortEvent arg0) {
        switch(arg0.getEventType()){
            case SerialPortEvent.BI:/*Break interrupt,通讯中断*/
            case SerialPortEvent.OE:/*Overrun error，溢位错误*/
            case SerialPortEvent.FE:/*Framing error，传帧错误*/
            case SerialPortEvent.PE:/*Parity error，校验错误*/
            case SerialPortEvent.CD:/*Carrier detect，载波检测*/
            case SerialPortEvent.CTS:/*Clear to send，清除发送*/
            case SerialPortEvent.DSR:/*Data set ready，数据设备就绪*/
            case SerialPortEvent.RI:/*Ring indicator，响铃指示*/
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空*/
                break;
            case SerialPortEvent.DATA_AVAILABLE:/*Data available at the serial port，端口有可用数据。读到缓冲数组，输出到终端*/
                byte[] readBuffer = new byte[1024*2];
                try {

                    while (inputStream.available() > 0) {
                        inputStream.read(readBuffer);
                        Thread.sleep(1000*4);
                        saveData(new String(readBuffer));
                    }

                } catch (IOException e) {
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }


    public void run() {
        try{
            Thread.sleep(threadTime);
            serialPort.close();
            log(String.format("端口''监听关闭了！", commPort.getName()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @方法名称 :close
     * @功能描述 :关闭 SerialPort
     * @返回值类型 :void
     */
    public void close(){
        System.out.println("监听关闭");
        serialPort.close();
        serialPort = null;
        commPort = null;
        serial = null;
    }


    public void log(String msg){
        System.out.println(appName+" --> "+msg);
    }

    /**
     * 输入数据
     * @param readData
     */
    public void saveData(String readData) {
        try {
            System.out.println(readData);
            System.out.println("分割线1--------");
            userSaveData(readData);
            if(state.length()>0 &&temperature.length()>0 && heartrate.length()>0 && longitude.length()>0 && latitude.length()>0){
            System.out.println("获取的数据：S="+state+"  "+"T="+temperature+"  "+"H="+heartrate+"  "+"X="+longitude+"  "+"Y="+latitude+"   "+"当前时间:"+updateDate);
            safeDao.insertData(state,temperature,heartrate,longitude,latitude,updateDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入数据失败");
        }
    }

    /**
     * 判断数据是否为有效数据
     * @param readData
     */
    public void userSaveData(String readData) throws ParseException {
        if(readData.length()>29 && (readData.substring(readData.indexOf(',')-1,readData.indexOf(',')).equals("1")||readData.substring(readData.indexOf(',')-1,readData.indexOf(',')).equals("0"))){
            String[] arr = readData.split(",");
            state=arr[0];
            temperature=arr[1];
            heartrate=arr[2];
            longitude=arr[3];
            if(Integer.parseInt(arr[4].substring(0,2))>9){
                latitude=arr[4].substring(0,8);
            }else{
                latitude=arr[4].substring(0,7);
            }
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            updateDate=df.parse(df.format(day));
        }

}

    static SafeDao safeDao ;
    public static void main(String[] args) throws Exception{
        ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml","classpath:mybatis-config.xml" );
        safeDao = (SafeDao)context.getBean("safeDao");
        Safe.getInstance("COM4");
    }

}

