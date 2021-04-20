package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileRespsitory {

    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    File getFile(Integer id);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File notes);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contentType}, filesize = #{fileSize}, filedata = #{fileData} WHERE fileId=#{fileId}")
    Integer update(File notes);

    @Delete("DELETE FILES NOTES WHERE fileId = #{id}")
    Integer delete(Integer id);

}
