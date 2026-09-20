import math
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('results.csv')

df['series'] = df['algorithm'] + " (" + df['input'] + ")"

def compute_ratio(row):
    n = row['n']
    comps = row['comparisons']
    if row['algorithm'] == 'QuickSelect':
        return comps / n
    else:
        return comps / (n * math.log2(n))

df['ratio'] = df.apply(compute_ratio, axis=1)

# 1. График: Time vs n
plt.figure(figsize=(10, 6))
for series_name, group in df.groupby('series'):
    plt.plot(group['n'], group['time_ms'], marker='o', label=series_name)
plt.xscale('log')
plt.yscale('log')
plt.xlabel('n (Log Scale)')
plt.ylabel('Time (ms, Log Scale)')
plt.title('Time vs n')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('time_vs_n.png', dpi=300)
plt.close()

# 2. График: Max recursion depth vs n
plt.figure(figsize=(10, 6))
for series_name, group in df.groupby('series'):
    plt.plot(group['n'], group['max_depth'], marker='s', label=series_name)
plt.xscale('log')
plt.xlabel('n (Log Scale)')
plt.ylabel('Max Recursion Depth')
plt.title('Max Recursion Depth vs n')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('depth_vs_n.png', dpi=300)
plt.close()

# 3. График: Ratio vs n
plt.figure(figsize=(10, 6))
for series_name, group in df.groupby('series'):
    plt.plot(group['n'], group['ratio'], marker='^', label=series_name)
plt.xscale('log')
plt.xlabel('n (Log Scale)')
plt.ylabel('Ratio (Comparisons / Scaling Factor)')
plt.title('Ratio vs n (Verifying Theta Bound)')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('ratio_vs_n.png', dpi=300)
plt.close()

print("Plots successfully generated: time_vs_n.png, depth_vs_n.png, ratio_vs_n.png")