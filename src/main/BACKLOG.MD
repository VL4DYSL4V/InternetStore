2) test all dao implementations
3)create method "saveIgnoreId" for sql dao classes
4) Too much stereotype code
5) Renaming fields, annotated with @JoinFormula in entity.Comment, 
will break comment DAO;
6) Replace iteration through collection of ids in HibernateUserDao.allEntities()
with JPQL queries;