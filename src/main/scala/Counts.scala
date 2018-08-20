import Tables.CountTableDef
import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.lifted.Tag
import slick.relational.RelationalProfile.ColumnOption
import Tables.TCountTableDef
import count.Count
import java.sql.{Connection,DriverManager}


object Tables {
  abstract class CountTableDef(tag: Tag) extends Table[Count](tag, "count") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def count = column[Int]("count")

  }

  class TCountTableDef(tag: Tag)
    extends CountTableDef(tag) {

    def * =( // sclinter:off Def type is complicated and adds no value
      id,
      name,
      count
    ) <> (
      (Count.apply _).tupled,
      Count.unapply)
  }

  type CountTableQuery =
    Query[
      TCountTableDef,
      Count,
      Seq
      ]

}


object CountController {
  val url = "jdbc:mysql://localhost/test"
  val username = "root"
  val password = "root"

//  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Server)

//  val counts = TableQuery[TCountTableDef]

  def get(id: Int): Count = {
//    dbConfig.db.run(counts.filter(_.id === id).result.headOption)
    val url = "jdbc:mysql://localhost/test?serverTimezone=UTC"
    val driver = "com.mysql.jdbc.Driver"
    val username = "root"
    val password = "root"
    var connection:Connection = null
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement
    val rs = statement.executeQuery("SELECT * FROM CountTable")
    rs.next()
    val id = rs.getString("id").toInt
    val name = rs.getString("name")
    val count = rs.getString("count").toInt
    println("id = %s, name = %s, count=%s".format(id,name, count))
    Count(id, name, count)
  }
}