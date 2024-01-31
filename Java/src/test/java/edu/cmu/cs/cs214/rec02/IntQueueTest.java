package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertEquals(null, mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(1);
        assertEquals(1, mQueue.peek().intValue());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), mQueue.dequeue());
            assertEquals(testList.size() - i - 1, mQueue.size());
        }
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }
    @Test
    public void testEnsureCapacity() {
        // Fill the queue to capacity
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        
        // Add one more element
        mQueue.enqueue(10);
        
        // Check that the queue has been resized
        assertTrue(mQueue.size() == 11);
        
        // the first element should be 0
        assertTrue(mQueue.peek() == 0); 
        
        
        // Check that the queue still behaves correctly
        for (int i = 0; i < 11; i++) {
            Integer dequeueValue = mQueue.dequeue();
            assertEquals(Integer.valueOf(i), dequeueValue);
        }
        
        // Check that the queue is empty
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testDequeueEmptyQueue() {
        assertEquals(null, mQueue.dequeue());
    }

    @Test
    public void testClear() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testCopyAfterEnsureCapacity() {
        ArrayIntQueue queue = new ArrayIntQueue();

        // Fill the queue to capacity and move head
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        // Dequeue 5 elements and move head
        for (int i = 0; i < 5; i++) {
            queue.dequeue();
        }

        // Add more element
        for (int i = 10; i < 20; i++) {
            queue.enqueue(i);
        }

    
        // It should be[5, 6, 7, 8, 9, 10, ..., 19]
        for (int expected = 5; expected < 20; expected++) {
            assertEquals(Integer.valueOf(expected), queue.dequeue());
        }
    }



}
