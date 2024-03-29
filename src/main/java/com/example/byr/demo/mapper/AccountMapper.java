package com.example.byr.demo.mapper;

import com.example.byr.demo.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @Delete({
        "delete from account",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @Insert({
        "insert into account (user_id, ",
        "info, ctime, utime)",
        "values (#{userId,jdbcType=BIGINT}, ",
        "#{info,jdbcType=VARCHAR}, #{ctime,jdbcType=DATE}, #{utime,jdbcType=DATE})"
    })
    int insert(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @InsertProvider(type=AccountSqlProvider.class, method="insertSelective")
    int insertSelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @Select({
        "select",
        "id, user_id, info, ctime, utime",
        "from account",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.DATE),
        @Result(column="utime", property="utime", jdbcType=JdbcType.DATE)
    })
    Account selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @UpdateProvider(type=AccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Tue Aug 20 18:52:04 CST 2019
     */
    @Update({
        "update account",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "info = #{info,jdbcType=VARCHAR},",
          "ctime = #{ctime,jdbcType=DATE},",
          "utime = #{utime,jdbcType=DATE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Account record);

    @Select({
        "select * from account where user_id = #{userId}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.DATE),
        @Result(column="utime", property="utime", jdbcType=JdbcType.DATE)
    })
    Account findAccountByUserId(@Param("userId") Integer userId);
}