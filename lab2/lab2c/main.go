package main

import "fmt"

func main() {
	chiEnergy := [16]int{2, 6, 523, 12, 6000, 52, 7, 4, 33, 22, 43, 423, 102, 64, 42, 109}
	result := start(chiEnergy)
	fmt.Println(result)
}
func start(array [16]int) int {
	c := make(chan int)
	go compute(array, 0, len(array)-1, c)
	return <-c
}
func compute(array [16]int, lo int, hi int, c chan int) {
	if lo == hi {
		c <- array[lo]
		return
	}
	c1 := make(chan int)
	c2 := make(chan int)
	go compute(array, lo, (lo+hi)/2, c1)
	go compute(array, (lo+hi)/2+1, hi, c2)
	energy1 := <-c1
	energy2 := <-c2
	if energy1 > energy2 {
		c <- energy1
	} else {
		c <- energy2
	}
}
