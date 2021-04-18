package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialRepository {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
    Credential getCredential(Integer id);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential notes);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid=#{credentialId}")
    Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    Integer delete(Integer id);

}
