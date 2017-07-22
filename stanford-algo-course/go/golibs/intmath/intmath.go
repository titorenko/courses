package intmath

func Min(x int, y int) int {
	if (x < y) {
		return x
	} else {
		return y
	}
}

func Max(x int, y int) int {
	if (x > y) {
		return x
	} else {
		return y
	}
}

func Max0(x int) uint {
	return uint(Max(x, 0))
}