#include <string>
#include <iostream>
#include <fstream>
#include <vector>
#include <ctime>
#include <algorithm>

#define CLOCKS_PER_MICROSEC (CLOCKS_PER_SEC / 1000000.0)

using namespace std;
typedef int_fast64_t int64;

vector<int64> load(const string& fileName) {
	std::fstream inFile(fileName);
	vector<int64> result;
	int64 val;
  	while(inFile >> val) 
  		result.push_back(val);
  	return result;
}

int solve(const vector<int64>& numbers, const int lowerBound, const int upperBound) {
	auto result = 0;
	
	vector<bool> visited(numbers.size());
	auto mark = [&] (int64 sum) { 
		auto idx = sum - lowerBound;
		if(!visited[idx]) {
			visited[idx] = true;
			result++;
		}
	};

	auto right = numbers.size() - 1;	
	for (auto left = 0; left < numbers.size(); left++) {

		auto x = numbers[left];
		for(auto j =  right + 1; j < numbers.size(); j++) {
			auto y = numbers[j];
			auto sum = x + y;
			if (sum > upperBound) {
				break;
			} else if (sum >= lowerBound && x != y) {
				mark(sum);
			}
		}

		for (auto j = right; j > left; j--) {
			auto y = numbers[j];
			auto sum = x + y;
			if (sum < lowerBound) {
				right = j;
				break;
			} else if (sum <= upperBound && x != y)  {
				mark(sum);
			} 
		}
	}
	return result;	
}


int main(void) {
    int t = 10000;
    auto numbers = load("../../java/src/main/resources/2sum.txt");    
    clock_t start = clock();
    sort(numbers.begin(), numbers.end());
    auto answer = solve(numbers, -t, t);
    clock_t end = clock();
    double elapsedMicros = double(end - start) / CLOCKS_PER_MICROSEC;
    cout << "Result is: " << answer << ", done in " << elapsedMicros << " micros" << endl;
}