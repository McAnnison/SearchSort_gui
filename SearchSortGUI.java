import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchSortGUI extends JFrame implements ActionListener {
    private JTextField inputField, searchField;
    private JTextArea resultArea;
    private JComboBox<String> algorithmSelector;
    private JButton runButton;

    private final String[] algorithms = {
            "Linear Search", "Binary Search",
            "Selection Sort", "Insertion Sort", "Merge Sort",
            "Bubble Sort", "Quick Sort", "Shell Sort",
            "Radix Sort", "Heap Sort", "Nearly Sorted", "Counting Sort"
    };

    public SearchSortGUI() {
        setTitle("Search & Sort Analyzer");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Input Section"));
        topPanel.setBackground(new Color(245, 245, 255));

        JLabel label1 = new JLabel("Enter numbers (comma-separated):");
        label1.setFont(new Font("SansSerif", Font.BOLD, 14));
        inputField = new JTextField();

        JLabel label2 = new JLabel("Enter search value (only for search):");
        label2.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchField = new JTextField();

        JLabel label3 = new JLabel("Select Algorithm:");
        label3.setFont(new Font("SansSerif", Font.BOLD, 14));
        algorithmSelector = new JComboBox<>(algorithms);
        algorithmSelector.setFont(new Font("SansSerif", Font.PLAIN, 14));

        runButton = new JButton("Run Algorithm");
        runButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        runButton.setBackground(new Color(30, 144, 255));
        runButton.setForeground(Color.WHITE);
        runButton.setFocusPainted(false);
        runButton.addActionListener(this);

        topPanel.add(label1);
        topPanel.add(inputField);
        topPanel.add(label2);
        topPanel.add(searchField);
        topPanel.add(label3);
        topPanel.add(algorithmSelector);
        topPanel.add(new JLabel());
        topPanel.add(runButton);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createTitledBorder("Results"));

        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(230, 240, 255));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputText = inputField.getText().trim();
        if (inputText.isEmpty()) {
            showError("Please enter a list of numbers.");
            return;
        }

        String[] tokens = inputText.split(",");
        int[] arr;
        try {
            arr = Arrays.stream(tokens).map(String::trim).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException ex) {
            showError("Invalid number format. Only integers allowed.");
            return;
        }

        String algorithm = (String) algorithmSelector.getSelectedItem();
        long start, end;

        switch (algorithm) {
            case "Linear Search":
                try {
                    int target = Integer.parseInt(searchField.getText().trim());
                    start = System.nanoTime();
                    int index = linearSearch(arr, target);
                    end = System.nanoTime();
                    resultArea.setText("Linear Search Result:\n" +
                            (index == -1 ? "Not found" : "Found at index " + index) +
                            "\nTime: " + (end - start) + " ns\nTime Complexity: O(n)");
                } catch (Exception ex) {
                    showError("Enter a valid number to search.");
                }
                break;

            case "Binary Search":
                Arrays.sort(arr);
                try {
                    int target = Integer.parseInt(searchField.getText().trim());
                    start = System.nanoTime();
                    int index = binarySearch(arr, target);
                    end = System.nanoTime();
                    resultArea.setText("Binary Search Result:\n" +
                            (index == -1 ? "Not found" : "Found at index " + index) +
                            "\nSorted Array: " + Arrays.toString(arr) +
                            "\nTime: " + (end - start) + " ns\nTime Complexity: O(log n)");
                } catch (Exception ex) {
                    showError("Enter a valid number to search.");
                }
                break;

            case "Selection Sort":
                start = System.nanoTime();
                selectionSort(arr);
                end = System.nanoTime();
                resultArea.setText("Selection Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n^2)");
                break;

            case "Insertion Sort":
                start = System.nanoTime();
                insertionSort(arr);
                end = System.nanoTime();
                resultArea.setText("Insertion Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n^2), O(n) best");
                break;

            case "Merge Sort":
                start = System.nanoTime();
                mergeSort(arr, 0, arr.length - 1);
                end = System.nanoTime();
                resultArea.setText("Merge Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n log n)");
                break;

            case "Bubble Sort":
                start = System.nanoTime();
                bubbleSort(arr);
                end = System.nanoTime();
                resultArea.setText("Bubble Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n^2)");
                break;

            case "Quick Sort":
                start = System.nanoTime();
                quickSort(arr, 0, arr.length - 1);
                end = System.nanoTime();
                resultArea.setText("Quick Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n log n)");
                break;

            case "Shell Sort":
                start = System.nanoTime();
                shellSort(arr);
                end = System.nanoTime();
                resultArea.setText("Shell Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n log n) average");
                break;

            case "Radix Sort":
                start = System.nanoTime();
                radixSort(arr);
                end = System.nanoTime();
                resultArea.setText("Radix Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(nk)");
                break;

            case "Heap Sort":
                start = System.nanoTime();
                heapSort(arr);
                end = System.nanoTime();
                resultArea.setText("Heap Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n log n)");
                break;

            case "Nearly Sorted":
                start = System.nanoTime();
                insertionSort(arr);
                end = System.nanoTime();
                resultArea.setText("Nearly Sorted (Insertion):\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n)");
                break;

            case "Counting Sort":
                start = System.nanoTime();
                arr = countingSort(arr);
                end = System.nanoTime();
                resultArea.setText("Counting Sort:\n" + Arrays.toString(arr) +
                        "\nTime: " + (end - start) + " ns\nTime Complexity: O(n + k)");
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int linearSearch(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) return i;
        }
        return -1;
    }

    private int binarySearch(int[] arr, int x) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m] == x) return m;
            if (arr[m] < x) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }

    private void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            int temp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = temp;
        }
    }

    private void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int[] L = Arrays.copyOfRange(arr, l, m + 1);
        int[] R = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = t;
                }
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        }
        int t = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = t;
        return i + 1;
    }

    private void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2)
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i], j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
    }

    private void radixSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) countingSortByDigit(arr, exp);
    }

    private void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n], count = new int[10];
        for (int value : arr) count[(value / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        System.arraycopy(output, 0, arr, 0, n);
    }

    private void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = arr[0]; arr[0] = arr[i]; arr[i] = t;
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            int t = arr[i]; arr[i] = arr[largest]; arr[largest] = t;
            heapify(arr, n, largest);
        }
    }

    private int[] countingSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        int min = Arrays.stream(arr).min().orElse(0);
        int range = max - min + 1;
        int[] count = new int[range];
        for (int value : arr) count[value - min]++;
        int idx = 0;
        for (int i = 0; i < range; i++)
            while (count[i]-- > 0) arr[idx++] = i + min;
        return arr;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchSortGUI::new);
    }
}
