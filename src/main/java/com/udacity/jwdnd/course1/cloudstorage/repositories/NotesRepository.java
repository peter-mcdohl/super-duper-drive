package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesRepository {

    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Notes getNotes(Integer id);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Notes> getUserNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Notes notes);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid=#{noteId}")
    Integer update(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    Integer delete(Integer id);

}
