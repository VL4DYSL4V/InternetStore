2) test all dao implementations
3)create method "saveIgnoreId" for sql dao classes
4) Too much stereotype code
5) Renaming fields, annotated with @JoinFormula in entity.Comment, 
will break comment DAO;
6) Replace iteration through collection of ids in HibernateUserDao.allEntities()
with JPQL queries;
7) Ask about comment and item description validation(html-insertions etc);
8) Create implementation of TextValidator to avoid insertion of absurd text
++++ 9) Add checks to mirror dataBase requirements
10) Eliminate duplicate code in all dao-s;

11)INCORRECT USAGE OF REJECTION IN VALIDATORS!! 
IT MUST USE INFORMATION FROM PROPERTIES, LIKE IN COUNTRYFORMVALIDATOR
12)Find out how to correctly display punishment time for users, who couldn't sign in 