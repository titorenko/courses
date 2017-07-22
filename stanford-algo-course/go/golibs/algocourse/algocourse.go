package algocourse

import (
	"io/ioutil"
	"strconv"
	"strings"
)

//Parse file with integers on every line into int slice
func ParseFileWithIntsOnEveryLine(fileName string) (numbers []int, err error) {
	bytes, err := ioutil.ReadFile(fileName)
	if err != nil {
		return nil, err
	}
	str := string(bytes)
	lines := strings.Split(str, "\n")
	numbers = make([]int, len(lines)-1)
	for idx, line := range lines[:(len(lines) - 1)] {
		n, err := strconv.Atoi(line)
		if err != nil {
			return nil, err
		}
		numbers[idx] = n
	}
	return numbers, nil
}

func ParseFileWithIntArraysOnEveryLine(fileName string) (numbers [][]int, err error) {
	bytes, err := ioutil.ReadFile(fileName)
	if err != nil {
		return nil, err
	}
	str := string(bytes)
	lines := strings.Split(str, "\n")
	numbers = make([][]int, len(lines)-1)
	for idx, line := range lines[:(len(lines) - 1)] {
		tokens := strings.Split(line, "\t")
		row := make([]int, len(tokens)-1)
		for ti, token := range tokens[:(len(tokens) - 1)] {
			n, err := strconv.Atoi(token)
			if err != nil {
				return nil, err
			}
			row[ti] = n
		}
		numbers[idx] = row
	}
	return numbers, nil
}
