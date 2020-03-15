package com.choimroc.mybatisplusdemo.application.users.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecordMapper extends BaseMapper<UserRecordEntity> {

    @Update("update users_record set total_course = #{totalCourse} where course = #{course}")
    int updateUserRecord(String course,String totalCourse);

    List<UserRecordEntity> queryUserRecordByUserId(@Param("user_id") String userId, @Param("course") String course);

    List<UserRecordEntity> queryUserRecord(@Param("user_id")String userId,@Param("username")String username,@Param("course")String course,@Param("course_sum")String courseSum,@Param("start_time")Long startTime,@Param("end_time")Long endTime);
}
