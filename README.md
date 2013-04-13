Steps to deploy this application: (I am making an assumption that you have necessary development tools like git, mysql, etc. installed)
1. Clone the repository using git
2. Import the database: 
	-Create a user with access to this database with username: root and password:root
	-A bzipped sql file can be found in the dbscripts directory. Import this file via mysql (a script(import.sh) is provided for Linux users)
3. Compile the src files
4. Run org.naruto.main.Launcher.java
5. Open a web browser (preferable Google Chrome) and browse to localhost:8080/deckBuilder2
6. Enjoy

If you have any questions or suggestions you can contact me at nbrady1@ycp.edu.