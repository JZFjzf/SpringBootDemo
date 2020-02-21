package com.choimroc.mybatisplusdemo.application.users.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecord;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordMapper extends BaseMapper<UserRecord> {

    @Select("select total_course from course_info where course=#{course}")
    String queryTotalCourse(String course);
}
