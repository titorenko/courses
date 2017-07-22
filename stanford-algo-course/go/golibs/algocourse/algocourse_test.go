package algocourse

import "testing"

func TestFileRead(t *testing.T) {
	numbers, err := ParseFileWithIntsOnEveryLine("IntegerArray.txt")
	if err != nil {
		t.Error("Expected to read the file", err)
	}
	if len(numbers) != 100000 {
		t.Error("Expected to 100000 entries", len(numbers))
	}
}
