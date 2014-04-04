package com.java7developer.example;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<T> {
	AtomicReference<Node<T>> head = new AtomicReference<>();

	public void push(T t) {
		Node<T> newHead = new Node<T>(t);
		Node<T> oldHead;
		do {
			oldHead = head.get();
			newHead.next = oldHead;
		} while (!head.compareAndSet(oldHead, newHead));
	}

	public T pop() {
		Node<T> oldHead;
		Node<T> newHead;
		do {
			oldHead = head.get();
			if (oldHead == null) {
				return null;
			}
			newHead = oldHead.next;
		} while (!head.compareAndSet(oldHead, newHead));

		return oldHead.item;
	}

	static class Node<T> {
		final T item;
		Node<T> next;

		public Node(T item) {
			this.item = item;
		}
	}
}
