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
        // TODO: write your own unit test
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
        assertEquals(1, mQueue.size());
        assertEquals(1, mQueue.peek().intValue());
    }


    @Test
    public void testPeekEmptyQueue() {
        // TODO: write your own unit test
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // TODO: write your own unit test
        mQueue.enqueue(1);
        mQueue.enqueue(2);

        assertEquals(1, mQueue.peek().intValue());
        assertEquals(2, mQueue.size());
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
        // TODO: write your own unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }

        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), mQueue.dequeue());
            assertEquals(testList.size() - i - 1, mQueue.size());
        }

        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
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
    public void testClear() {
        // Test clear on empty queue
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());

        // Test clear on non-empty queue
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        assertEquals(3, mQueue.size());
        
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    @Test
    public void testDequeueEmptyQueue() {
        // Test dequeue on empty queue returns null
        assertNull(mQueue.dequeue());
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testEnsureCapacity() {
        // Test array resizing by adding more than initial capacity (10 elements)
        for (int i = 1; i <= 15; i++) {
            mQueue.enqueue(i);
        }
        
        assertEquals(15, mQueue.size());
        
        // Verify all elements are in correct order
        for (int i = 1; i <= 15; i++) {
            assertEquals(i, mQueue.dequeue().intValue());
        }
        
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEnsureCapacityWithWrapping() {
        // Test array resizing when head is not at index 0 (circular array scenario)
        // First fill the queue
        for (int i = 1; i <= 10; i++) {
            mQueue.enqueue(i);
        }
        
        // Dequeue some elements to move head forward
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, mQueue.dequeue().intValue());
        }
        
        // Now add more elements to trigger resize with head in middle
        for (int i = 11; i <= 20; i++) {
            mQueue.enqueue(i);
        }
        
        assertEquals(15, mQueue.size());
        
        // Verify all remaining elements are in correct order
        for (int i = 6; i <= 20; i++) {
            assertEquals(i, mQueue.dequeue().intValue());
        }
        
        assertTrue(mQueue.isEmpty());
    }
    
}
