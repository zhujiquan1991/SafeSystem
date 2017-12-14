<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>TEST</title>
    <script src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
    <script type="text/javascript" charset="utf-8">
        /**
         * 每10秒自动刷新一次数据
         * **/
        window.onload=function(){
            sendRequest();
            setInterval(function(){
                    sendRequest();},
                10000);
        }
        /**
         * 读取数据，对应数据接口
         *  @constructor
         * **/
        function sendRequest(){
            $.ajax({
                url:"/mgr/one.do",    //请求的url地址
                dataType:"json",   //返回格式为json
                async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                type:"GET",   //请求方式
                success:function(data){
                    SeeData(data);
                },
            });
        };
        /**
         * 数据处理
         * **/
        function SeeData(data) {
            var state=new Array(5);
            var temperature=new Array(5);
            var heartrate=new Array(5);
            var longitude=new Array(5);
            var latitude=new Array(5);
            var upd=new Array(5);
            var updateDate=new Array(5);
            $.each(data, function (index) {
                temperature[index] = data[index].temperature;
                heartrate[index] = data[index].heartrate;
                longitude[index] = data[index].longitude;
                latitude[index] = data[index].latitude;
                upd[index] = new Date(data[index].updateDate);
                updateDate[index]=upd[index].getFullYear()+"-"+(upd[index].getMonth() + 1)+"-"+upd[index].getDay()+" "+ (upd[index].getHours() < 10 ? "0" + +upd[index].getHours() :  +upd[index].getHours()) +":"+ (upd[index].getMinutes() < 10 ? "0" + +upd[index].getMinutes() :  +upd[index].getMinutes()) +":"+ (upd[index].getSeconds() < 10 ? "0" + +upd[index].getSeconds() : +upd[index].getSeconds());
                if(data[index].state=="0"){state[index] = "<font color=\"#66cc66\">"+"安全"+"</font>"; }else {state[index] = "<font color=\"#ff331d\">"+"危险"+"</font>"; };
           if(index==0){
                    $("#TestWD").html(temperature[index]);
                    $("#TestSD").html(heartrate[index]);
                    $("#TestTQ").html(state[index]);
                };
                if(index>0){
                    $("#TestLS11").html("&nbsp;&nbsp;"+"<font color=\"#FF0000\">"+temperature[1]+"</font>"+"&nbsp;&nbsp;"+"<font color=\"#1404ff\">"+heartrate[1]+"</font>"+"<br>"+"<font color=\"#ff8500\">"+updateDate[1]+"</font>");
                    $("#TestLS21").html("&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color=\"#0dff1d\">"+state[1]+"</font>"+"&nbsp;&nbsp;"+"<br>"+"<font color=\"#ff0cd2\">"+longitude[1]+"&nbsp;"+"<font color=\"#ff8500\">"+latitude[1]+"</font>");
                    $("#TestLS12").html("&nbsp;&nbsp;"+"<font color=\"#FF0000\">"+temperature[2]+"</font>"+"&nbsp;&nbsp;"+"<font color=\"#1404ff\">"+heartrate[2]+"</font>"+"<br>"+"<font color=\"#ff8500\">"+updateDate[2]+"</font>");
                    $("#TestLS22").html("&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color=\"#0dff1d\">"+state[2]+"</font>"+"&nbsp;&nbsp;"+"<br>"+"<font color=\"#ff0cd2\">"+longitude[2]+"&nbsp;"+"<font color=\"#ff8500\">"+latitude[2]+"</font>");
                    $("#TestLS13").html("&nbsp;&nbsp;"+"<font color=\"#FF0000\">"+temperature[3]+"</font>"+"&nbsp;&nbsp;"+"<font color=\"#1404ff\">"+heartrate[3]+"</font>"+"<br>"+"<font color=\"#ff8500\">"+updateDate[3]+"</font>");
                    $("#TestLS23").html("&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color=\"#0dff1d\">"+state[3]+"</font>"+"&nbsp;&nbsp;"+"<br>"+"<font color=\"#ff0cd2\">"+longitude[3]+"&nbsp;"+"<font color=\"#ff8500\">"+latitude[3]+"</font>");
                    $("#TestLS14").html("&nbsp;&nbsp;"+"<font color=\"#FF0000\">"+temperature[4]+"</font>"+"&nbsp;&nbsp;"+"<font color=\"#1404ff\">"+heartrate[4]+"</font>"+"<br>"+"<font color=\"#ff8500\">"+updateDate[4]+"</font>");
                    $("#TestLS24").html("&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color=\"#0dff1d\">"+state[4]+"</font>"+"&nbsp;&nbsp;"+"<br>"+"<font color=\"#ff0cd2\">"+longitude[4]+"&nbsp;"+"<font color=\"#ff8500\">"+latitude[4]+"</font>");
                };
            });
        }


        function Location(){
            console.log("进入Location");
            $.ajax({
                url:"/mgr/two.do",
                dataType:"text",
                async:true,
                type:"GET",
                success:function(data){
                    console.log("success");
                    $("#TestSY").html(data);
                },
            });
        };

    </script>
</head>
<body>

<style>
    *{margin:0px;padding:0px;}
    .content{width:796px;height:396px;border:3px solid #000000;}
    .Header{width:802px;height:50px;position:fixed;left:0px;top:0px;background:#66cc66;}
    .WD{width:200px;height:300px;position:fixed;left:0px;top:50px;border:1px solid #000000;}
    .SD{width:200px;height:300px;position:fixed;left:200px;top:50px;border:1px solid #000000;}
    .LS1{width:200px;height:300px;position:fixed;left:400px;top:50px;border:1px solid #000000;}
    .LS2{width:200px;height:300px;position:fixed;left:600px;top:50px;border:1px solid #000000;}
    .TQ{width:266px;height:50px;position:fixed;left:0px;top:350px;border:1px solid #000000;}
    .SY{width:534px;height:50px;position:fixed;left:266px;top:350px;border:1px solid #000000;}
    .Location{width:100px;height:24px;position:fixed;left:688px;top:362px;}
    .TestWD{position:fixed;left:15px;top:160px;color: #ff331d;Font-weight:bold;Font-size:80px;}
    .TestSD{position:fixed;left:235px;top:160px;color: #1404ff;Font-weight:bold;Font-size:80px;}
    .TestTQ{position:fixed;left:100px;top:360px;Font-weight:bold;Font-size:30px;}
    .TestSY{position:fixed;left:350px;top:365px;Font-weight:bold;Font-size:20px;}
    .TestLS11{position:fixed;left:410px;top:100px;Font-weight:bold;Font-size:20px;}
    .TestLS12{position:fixed;left:410px;top:155px;Font-weight:bold;Font-size:20px;}
    .TestLS13{position:fixed;left:410px;top:210px;Font-weight:bold;Font-size:20px;}
    .TestLS14{position:fixed;left:410px;top:265px;Font-weight:bold;Font-size:20px;}
    .TestLS21{position:fixed;left:602px;top:100px;Font-weight:bold;Font-size:20px;}
    .TestLS22{position:fixed;left:602px;top:155px;Font-weight:bold;Font-size:20px;}
    .TestLS23{position:fixed;left:602px;top:210px;Font-weight:bold;Font-size:20px;}
    .TestLS24{position:fixed;left:602px;top:265px;Font-weight:bold;Font-size:20px;}
</style>

<div class="content"  id="content">
    <div class="Header" style="text-align: center;"><h1>安全系统</h1></div>
    <div class="WD"><h1>温<br>度</h1></div><div class="TestWD" id="TestWD"></div>
    <div class="SD"><h1>心<br>率</h1></div><div class="TestSD" id="TestSD"></div>
    <div class="LS1"><h4>历史记录：<br>（温度·心率·时间)</h4></div>
    <div class="LS2"><h4>历史记录：<br>（状态·经度·纬度)</h4></div>
    <div class="TQ"><h4><br>状态：</h4></div><div class="TestTQ" id="TestTQ"></div>
    <div class="SY"><h4><br>地理位置：</h4></div><div class="TestSY" id="TestSY"></div>
    <tr><button class="Location" id="Location" onclick="Location()">获取最新位置</button></tr>
</div>

<div class="TestLS11" id="TestLS11"></div><div class="TestLS12" id="TestLS12"></div><div class="TestLS13" id="TestLS13"></div><div class="TestLS14" id="TestLS14"></div>
<div class="TestLS21" id="TestLS21"></div></div><div class="TestLS22" id="TestLS22"></div><div class="TestLS23" id="TestLS23"></div><div class="TestLS24" id="TestLS24"></div>
</body>
</html>