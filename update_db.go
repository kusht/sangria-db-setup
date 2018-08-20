package main


import "database/sql"
import _ "github.com/go-sql-driver/mysql"


func main() {
	db,err := sql.Open("mysql", "root:root@localhost/test")
	if err != nil {
		println(err)
	}
	defer db.Close()




}
