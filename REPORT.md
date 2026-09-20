# Assignment 1: Divide and Conquer Report

## 1. Asymptotic Bounds Summary

| Algorithm | Best | Average | Worst | Reason / Input Details |
| :--- | :---: | :---: | :---: | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Always splits in half; merge step takes linear $\Theta(n)$ time. |
| **QuickSort** | $\Theta(n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Linear on duplicates due to 3-way partition; random pivot and smaller-side recursion prevent $O(n^2)$ depth. |
| **QuickSelect** | $\Theta(n)$ | $\Theta(n)$ | $O(n^2)$ | Recurses into one half only on average ($T(n) = T(n/2) + \Theta(n)$). Worst case is extremely unbalanced splits. |
| **Insertion Sort** | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | Best case on already sorted arrays (0 shifts). Worst case on reverse sorted order. |

---

## 2. Recurrence Relations and Master Theorem

General form: $T(n) = a \cdot T(n/b) + f(n)$ with critical exponent $c = \log_b(a)$.

* **MergeSort**:
    * Recurrence: $T(n) = 2T(n/2) + \Theta(n)$
    * Parameters: $a = 2, b = 2, f(n) = \Theta(n)$
    * $\log_2(2) = 1 \implies f(n) = \Theta(n^1)$ matches **Case 2**.
    * **Result**: $T(n) = \Theta(n \log n)$.

* **QuickSort** (balanced split assumption):
    * Recurrence: $T(n) = 2T(n/2) + \Theta(n)$
    * Parameters: $a = 2, b = 2, f(n) = \Theta(n)$
    * $\log_2(2) = 1 \implies$ matches **Case 2** $\implies \Theta(n \log n)$.
    * **Random Pivot Justification**: Random pivot ensures balanced partitions with high probability. Even biased splits (e.g., $10\%/90\%$) bound depth to $O(\log n)$ with linear work per level, preserving an expected $O(n \log n)$ runtime.

* **QuickSelect** (balanced split assumption):
    * Recurrence: $T(n) = 1 \cdot T(n/2) + \Theta(n)$
    * Parameters: $a = 1, b = 2, f(n) = \Theta(n)$
    * $\log_2(1) = 0$. Since $f(n) = \Theta(n^1) = \Omega(n^{0+\epsilon})$ and regularity condition holds ($1 \cdot (n/2) \le c \cdot n$ for $c = 1/2$), this matches **Case 3**.
    * **Result**: $T(n) = \Theta(n)$.

---

## 3. Plots

* **Time vs n**: `time_vs_n.png`
* **Max Recursion Depth vs n**: `depth_vs_n.png`
* **Ratio vs n**: `ratio_vs_n.png`

---

## 4. Empirical $\Theta$-Bound Check

By definition, $c_1 \cdot g(n) \le f(n) \le c_2 \cdot g(n)$ for $n \ge n_0$.
* **MergeSort & QuickSort**: Ratio $\frac{\text{comparisons}}{n \log_2(n)}$ flattens out for $n \ge 10\,000$.
    * $c_1 \approx 1.1$
    * $c_2 \approx 2.4$
    * $n_0 = 10\,000$
* **QuickSelect**: Ratio $\frac{\text{comparisons}}{n}$ stabilizes between $1.5$ and $3.5$ for $n \ge 10\,000$, validating the linear bound $\Theta(n)$.

---

## 5. Discussion

1. Empirical execution times and operation counts align with theoretical $O(n \log n)$ and $O(n)$ bounds.
2. Slight deviations at small $n = 1\,000$ stem from the Insertion Sort cutoff ($N \le 15$), where quadratic overhead is negligible compared to recursive function call overhead.
3. Warm-up iterations and median filtering across 5 runs eliminated JIT compilation spikes and Garbage Collection pauses.
4. QuickSort stack depth remained strictly bounded within $2 \cdot \log_2(n)$ across all input types due to smaller-side recursion.