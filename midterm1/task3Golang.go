package main

import (
	"fmt"
	"sync"
	"time"
)

var places [5]bool
var freeSpaces int = 5
var lock sync.Mutex

func unparking() {

}
func parking(semaphore chan bool, id int) {
	select {
	case <-semaphore:
		lock.Lock()
		var number int
		for i := 0; i < 5; i++ {
			if places[i] {
				fmt.Printf("Car %d parked at place %d\n", id, i)
				freeSpaces--
				places[i] = false
				number = i
				if freeSpaces > 0 {
					semaphore <- true
				}
				lock.Unlock()
				time.Sleep(time.Second * 5)
				break
			}
		}

		freeSpaces++
		lock.Lock()
		places[number] = true
		lock.Unlock()
		if freeSpaces == 1 {
			semaphore <- true
		}
		fmt.Printf("Car %d has left parking place!\n", id)
	case <-time.After(3 * time.Second):
		fmt.Println("Car %d went to other parking!\n")
	}
}
func main() {
	for i := 0; i < 5; i++ {
		places[i] = true
	}
	var semaphore = make(chan bool, 1)
	semaphore <- true
	for i := 0; i < 10; i++ {
		go parking(semaphore, i)
		time.Sleep(time.Millisecond * 500)
	}

	fmt.Scanln()
}
