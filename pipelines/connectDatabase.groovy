//@Library('sharedLibrary')_
//import com.foo.DBConnection;
import groovy.sql.Sql;

//def db=new DBConnection()


node() {
	
 stage('clone') {
     git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/pipelines.git'
  }	
  stage ('connection') {
    /**def classLoader = ClassLoader.systemClassLoader
    while (classLoader.parent) {
        classLoader = classLoader.parent
    }
    classLoader.addURL(new File("$WORKSPACE/resources/mysql-connector-java-8.0.28.jar").toURL())
    def sql = Sql.newInstance("jdbc:mysql://host.docker.internal:3306/employee", "root", "root@pass", "com.mysql.jdbc.Driver")
    sql.firstRow('SELECT employee_id, employee_name FROM employee_master')
    sql.close()**
    db.getConnection(this)
  }
        
	
}
    
