# Divide-and-Conquer Algorithms Benchmark
Recursion Depth Control
- **MergeSort**: Natural depth ~log₂(n), controlled by small-n cutoff (16 elements)
- **QuickSort**: Tail recursion optimization + smaller-partition-first ensures O(log n) depth
- **DeterministicSelect**: Iterative approach with guaranteed O(n) time
- **ClosestPair**: Standard D&C with strip optimization

Memory Management
- MergeSort uses reusable buffer to avoid repeated allocations
- QuickSort operates in-place with O(1) additional space
- DeterministicSelect uses temporary arrays for median calculations
- ClosestPair clones and sorts points arrays

# Recurrence Analysis
MergeSort
**Recurrence**: T(n) = 2T(n/2) + Θ(n)  
**Master Theorem**: Case 2 (a = 2, b = 2, f(n) = Θ(n))  
**Solution**: Θ(n log n)

QuickSort
**Recurrence**: T(n) = T(k) + T(n-k-1) + Θ(n) where k is partition size  
**Akra-Bazzi Intuition**: Expected k ≈ n/2 → T(n) = 2T(n/2) + Θ(n) → Θ(n log n)  
**Worst-case**: O(n²) but randomized pivot makes this negligible

Deterministic Select
**Recurrence**: T(n) ≤ T(⌈n/5⌉) + T(7n/10 + 6) + O(n)  
**Solution**: T(n) = O(n) - geometric series converges

Closest Pair
**Recurrence**: T(n) = 2T(n/2) + O(n)  
**Master Theorem**: Case 2 → Θ(n log n)

Performance Analysis

# Time Complexity Validation
All algorithms demonstrate expected theoretical complexities:
- MergeSort: O(n log n) consistent
- QuickSort: O(n log n) expected, O(n²) worst-case avoided via randomization
- Deterministic Select: O(n) verified across sizes
- Closest Pair: O(n log n) vs O(n²) brute force

# Recursion Depth
- MergeSort depth: ~log₂(n) as expected
- QuickSort depth: ~1.5*log₂(n) due to smaller-first optimization
- Deterministic Select depth: O(log n) due to iterative approach
- Closest Pair depth: log₂(n) for D&C structure

# Constant Factors & Cache Effects
- MergeSort suffers from array copying but benefits from sequential access
- QuickSort excels due to cache-friendly partitioning
- Deterministic Select has higher constants from median computations
- Closest Pair benefits from spatial locality in strip checking

# Summary: Theory vs Practice

Strong Alignment:
- All algorithms match theoretical time complexity bounds
- Recursion depth bounds respected
- Memory usage patterns as expected

Notable Observations:
- QuickSort outperforms MergeSort in practice despite same complexity
- Deterministic Select's O(n) only beneficial for large n due to constants
- Closest Pair's strip optimization crucial for practical performance

# Usage

```bash
Run benchmarks
mvn compile exec:java -Dexec.mainClass="cli.BenchmarkRunner"

Run tests
mvn test

# Generate header for CSV results
mvn compile exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="header"
