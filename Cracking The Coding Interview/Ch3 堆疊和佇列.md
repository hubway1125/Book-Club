# Chapter 3 堆疊和佇列
## 堆疊
1. 後進先出 (Last In First Out, LIFO)
2. 操作：
   3. pop() : 取出並刪除最頂端的項目
   4. push(item) : 添加一個項目到最頂部
   5. peek() : 回傳最頂部的項目(但不刪除)
   6. isEmpty() : 堆疊為空則回傳 true
7. 不提供對第i個元素的存取。加入和刪除能在常數時間內完成。
8. 簡單實作範例：
    ```java
        public class MyStack<T> {
            private static class StackNode<T> {
                private T data;
                private StackNode<T> next;
                public StackNode(T data) {
                    this.data = data;
                }
            }
        
            private StackNode<T> top; // has data and a StackNode next
   
            public T pop() {
                if (top == null) throw new EmptyStackException();
                T item = top.data;
                top = top.next;
                return item;
            }
        
            public void push(T item) {
                StackNode<T> t = new StackNode<T>(item);
                t.next = top;
                top = t;
            }
        
            public T peek() {
                if (top == null) throw new EmptyStackException();
                return top.data;
            }
        
            public boolean isEmpty() {
                return top == null;
            }
        }
    ```
   
## 佇列
1. 先進先出 (First In First Out, FIFO)
2. 操作：
   3. add(item) : 在最尾端添加一個項目
   4. remove() : 刪除第一項
   5. peek() : 回傳最頂部的項目
   6. isEmpty() : 佇列為空則回傳 true
7. 簡單實作範例：
   ```java
    public class MyQueue<T> {
        private static class QueueNode<T> {
            private T data;
            private QueueNode<T> next;
    
            public QueueNode(T data) {
                this.data = data;
            }
        }
        
        private QueueNode<T> first;
        private QueueNode<T> last;
        
        public void add(T item){
            QueueNode<T> t = new QueueNode<T>(item);
            if (last != null) {
                last.next = t;
            }
            last = t;
            if (first != null) {
                first = last;
            }
        }
        
        
        public T remove() {
            if (first == null)  throw new NoSuchElementException();
            T data = first.data;
            first = first.next;
            if (first == null) {
                last = null;
            }
            return data;
        }
        
        public T peek() {
            if (first == null)  throw new NoSuchElementException();
            return first.data;
        }
        
        public boolean isEmpty() {
            return first == null;
        }
    }
   ```
   
8. 注意事項：
   9. 更新第一個和最後一個節點時常會出錯，要多檢查。
   10. 經常使用佇列的時機是：寬度優先搜尋(BFS)、實作快取。
