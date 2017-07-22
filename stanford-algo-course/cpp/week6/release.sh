#!/bin/bash

g++ -std=c++11 -O3 -march=native -flto -fwhole-program -o 2sum-release src/2sum.cc