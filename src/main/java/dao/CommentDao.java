package dao;

import entity.Comment;
import exception.dao.FetchException;

import java.util.Collection;

public interface CommentDao extends CrudDao<Long, Comment> {

    Collection<Comment> commentsWithUserId(Long userId) throws FetchException;

    Collection<Comment> commentsWithItemId(Long itemId) throws FetchException;

}
