package main

import "database/sql"
import _ "github.com/go-sql-driver/mysql"
import "fmt"
import (
	"rubrik/common-go/message-queue"
	"context"
	//"reflect"
	"github.com/streadway/amqp"
	"reflect"
)

func main() {
	ctx := context.Background()
	db,err := sql.Open("mysql", "root:root@tcp(localhost)/test")
	fmt.Println("hi")
	if err != nil {
		fmt.Println(err)
		return
	}

	stmt, err := db.Prepare("UPDATE CountTable SET count=9 WHERE id=1")

	failOnError(err, "could not update db")

	_, err = stmt.Exec()

	failOnError(err, "could not execute sql")

	fmt.Println("updating rabbit mq")
	updateTable(ctx)

	defer db.Close()


}

func updateTable(ctx context.Context) {
//have another message queue running from the common go base queue and push to that
///connect_to_queue(ctx)
publish_native()
//connect_to_queue(ctx)
}

func connect_to_queue(ctx context.Context) {
	conn, err := queue.Connect()
	//conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	defer conn.Close()
	failOnError(err, "failed to connect")
	channel, err := conn.Channel()
	failOnError(err, "failed to retrieve channel")
	defer channel.Close()

	q, err := channel.QueueDeclare(
		"hello", // name
		false,   // durable
		false,   // delete when unused
		false,   // exclusive
		false,   // no-wait
		nil,     // arguments
	)

	failOnError(err, "Failed to declare a queue")

	//body := "database changed"
	//err = channel.Publish(
	//	"",     // exchange
	//	q.Name, // routing key
	//	false,  // mandatory
	//	false,  // immediate
	//
	//	amqp.Publishing {
	//		ContentType: "text/plain",
	//		Body:        []byte(body),
	//	})
	//failOnError(err, "Failed to publish a message")
	state, err := channel.QueueInspect("hello")
	fmt.Println(state)
	failOnError(err, "could not get state of hello queue")
}

func failOnError(err error, msg string) {
	if err != nil {
		panic(fmt.Sprintf("%s: %s", msg, err))
	}
}

func publish_native() {
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()
	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()


	q, err := ch.QueueDeclare(
		"hello", // name
		false,   // durable
		false,   // delete when unused
		false,   // exclusive
		false,   // no-wait
		nil,     // arguments
	)
	failOnError(err, "Failed to declare a queue")
	body := "database changed"
	err = ch.Publish(
		"",     // exchange
		q.Name, // routing key
		false,  // mandatory
		false,  // immediate
		amqp.Publishing {
			ContentType: "text/plain",
			Body:        []byte(body),
		})
	failOnError(err, "Failed to publish a message")
}

//func check_queue(name string) {
//
//}
