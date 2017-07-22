package main

/*
type UF struct {
	id   []int  // id[i] = parent of i
	rank []byte // rank[i] = rank of subtree rooted at i (cannot be more than int bit size)
}

func NewUF(size int) *UF {
	result := new(UF)
	result.rank = make([]byte, size)
	result.id = make([]int, size)
	for i, _ := range result.rank {
		result.id[i] = i
	}
	return result
}

func (this *UF) Connected(p int, q int) bool {
	return this.Find(p) == this.Find(q)
}

func (this *UF) Find(p int) int {
	for p != this.id[p] {
		this.id[p] = this.id[this.id[p]] // path compression by halving
		p = this.id[p]
	}
	return p
}

func (this *UF) Union(p int, q int) {
	i := this.Find(p)
	j := this.Find(q)
	if i == j {
		return
	} else if this.rank[i] < this.rank[j] { // make root of smaller rank point to root of larger rank
		this.id[i] = j
	} else if this.rank[i] > this.rank[j] {
		this.id[j] = i
	} else {
		this.id[j] = i
		this.rank[i]++
	}
}
*/
