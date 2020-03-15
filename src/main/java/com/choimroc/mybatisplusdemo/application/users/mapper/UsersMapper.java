package com.choimroc.mybatisplusdemo.application.users.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.choimroc.mybatisplusdemo.application.users.entity.UsersEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表示此接口为操作数据库的Mapper接口
 * @Mapper
 */
@Repository
public interface UsersMapper extends BaseMapper<UsersEntity> {
    //
//    /**
//     * 插入一个用户
//     *
//     * @param user
//     * @return
//     */
//    @Options(useGeneratedKeys = true, keyProperty = "id") //自动生成主键，会封装至对象中
//    @Insert("insert into users (userid,username,imgurl,times,showtime) values (#{userid}, #{username},#{imgurl},#{times},#{showtime})")
//    int insertUser(Users user);
//
//    @Options(useGeneratedKeys = true, keyProperty = "id") //自动生成主键，会封装至对象中
//    @Insert("insert into users_record (userid,username,times,showtime,result,imgurl,course,coursesum,totalcourse,similarity) values (#{userid}," +
//            " #{username}, #{times}, #{showtime},#{result},#{imgurl},#{course},#{coursesum},#{totalcourse},#{similarity})")
//    int insertUserRecord(UserRecord userRecord);
//
//    @Select("select totalcourse from courseinfo where course=#{course}")
//    String queryTotalCourse(String course);
//    /**
//     * 根据ID删除一个用户
//     * @param id
//     * @return
//     */
//    @Delete("delete from users where id = #{id}")
//    int deleteUserById(Integer id);
//
//    /**
//     * 根据ID修改用户
//     * @param user
//     * @return
//     */
//    @Update("update users set username = #{username}, password = #{password}, phone = #{phone} where id = #{id}")
//    int updateUser(Users user);
//    @Update("update users_record set totalcourse = #{totalcourse} where course = #{course}")
//    int updateUserRecord(String course,String totalcourse);
//
//
//    int insertOrUpdateCourseInfo(String course,String totalcourse);
//    //返回所有记录
//    @Select("select * from users_record ")
//    List<UserRecord> queryUserRecordAll();
//
//    int insertinfo(@Param("userid") String userid, @Param("username") String username, @Param("clazz")String clazz, @Param("times")Long times,
//                   @Param("inserttime") String inserttime, @Param("course") String course, @Param("coursesum") String coursesum, @Param("showtime") String showtime, @Param("status") String status);
//
//
//
    List<UsersEntity> queryUsers(@Param("user_id")String userId, @Param("username")String username, @Param("start_time")Long startTime, @Param("end_time")Long endTime);
//
//    @Delete("delete from studentrecord")
//    int deleteStudentInfo();
//
//    @Delete("delete from courseinfo")
//    int deleteCourseInfo();
//
//    List<UserRecord> queryUserRecordByuserid(String userid,String course);
//
//    List<UserRecord> queryUserRecord(String userid,String username,String course,String coursesum,Long starttime,Long endtime);
    @Select("select * from ( select * from users order by times desc limit 100000 ) as t group by id,user_id")
    List<UsersEntity> queryUsersAll();
}
