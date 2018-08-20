package main


import "database/sql"
import _ "github.com/go-sql-driver/mysql"
import "fmt"

func main() {
	db,err := sql.Open("mysql", "root:root@tcp(localhost)/test")
	if err != nil {

		fmt.Println(err)
		return
	}
	stmt, err := db.Prepare("UPDATE CountTable SET count=6 WHERE id=1")
	if err!= nil {
		fmt.Println(err)
		return
	}
	res, err := stmt.Exec()
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Println(res)
	defer db.Close()




}

func updateTable() {
//have another message queue running from the common go base queue and push to that

}


