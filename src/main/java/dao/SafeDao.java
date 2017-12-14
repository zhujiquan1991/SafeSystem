package dao;

import entity.SafeEntity;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */
public interface SafeDao {
    /**
     * 写入数据库
     */
    int insertData(@Param("state") String state, @Param("temperature") String temperature, @Param("heartrate") String heartrate, @Param("longitude") String longitude, @Param("latitude") String latitude, @Param("updateDate") Date updateDate);
    List<SafeEntity> getList();
    List<SafeEntity> getOne();

}
