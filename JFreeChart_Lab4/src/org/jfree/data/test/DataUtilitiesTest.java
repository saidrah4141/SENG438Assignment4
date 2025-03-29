package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class DataUtilitiesTest extends DataUtilities {
	
	 private Mockery mockingContext;
     private Values2D values;
     private KeyedValues kValues;
     private KeyedValues resultValue;
	 
	 @Before
	 public void setUp() throws Exception {
		 mockingContext = new Mockery();
	     values = mockingContext.mock(Values2D.class);
	     kValues = mockingContext.mock(KeyedValues.class);
	 }
	
	 
	 @Test
	    public void testEqualForTwoArrays() {
	        double[][] array1 = {
	            {1.0, 2.0},
	            {3.0, 4.0}
	        };
	        double[][] array2 = {
	            {1.0, 2.0},
	            {3.0, 4.0}
	        };
	        
	        boolean result = DataUtilities.equal(array1, array2);
	        assertTrue(result);
	    }
	 
	 @Test
	    public void testCloneForArray() {
	        double[][] array1 = {
	            {1.0, 2.0},
	            {3.0, 4.0}
	        };
	        
	        double [][] result = DataUtilities.clone(array1);

	        assertEquals(array1.length, result.length);
	    }

	 
	 @Test
	 public void testCalculateColumnTotal_ValidRows() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(5));  
	             one(values).getValue(1, 0);
	             will(returnValue(10.0));
	             one(values).getValue(3, 0);
	             will(returnValue(20.0));
	         }
	     });

	     int[] validRows = {1, 3}; // Only these rows are valid
	     double result = DataUtilities.calculateColumnTotal(values, 0, validRows);

	     assertEquals("Total should sum 10.0 + 20.0", 30.0, result, .000000001d);
	 }
	 
	 @Test
	 public void calculateColumnTotalForTwoValues() {

	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(2));
	             one(values).getValue(0, 0);
	             will(returnValue(7.5));
	             one(values).getValue(1, 0);
	             will(returnValue(2.5));
	         }
	     });
	     double result = DataUtilities.calculateColumnTotal(values, 0);
	     
	     assertEquals(10.0, result, .000000001d);
	    
	 }
	 
	 @Test
	 public void calculateColumnTotalForNullValues() {

	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(0));
	         }
	     });
	     double result = DataUtilities.calculateColumnTotal(values, 0);
	     
	     assertEquals(0, result, .000000001d);
	     
	 }
	 
	 @Test
	 public void testCalculateColumnTotal_NullValueIgnored_W_VR() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(3)); 
	             one(values).getValue(0, 0);
	             will(returnValue(10.0)); //  valid
	             one(values).getValue(1, 0);
	             will(returnValue(null)); //  should be ignored
	             one(values).getValue(2, 0);
	             will(returnValue(20.0)); //  valid
	         }
	     });

	     int[] validRows = {0, 1, 2}; 
	     double result = DataUtilities.calculateColumnTotal(values, 0, validRows);

	     assertEquals("Total should sum only non-null values: 10.0 + 20.0", 30.0, result, 1e-9);
	 }
	 
	 @Test
	 public void testCalculateColumnTotal_NullValueIgnored_WO_VR() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(3)); 
	             one(values).getValue(0, 0);
	             will(returnValue(10.0)); //  valid
	             one(values).getValue(1, 0);
	             will(returnValue(null)); //  should be ignored
	             one(values).getValue(2, 0);
	             will(returnValue(20.0)); //  valid
	         }
	     });
 
	     double result = DataUtilities.calculateColumnTotal(values, 0);

	     assertEquals("Total should sum only non-null values: 10.0 + 20.0", 30.0, result, 1e-9);
	 }
	 
	 @Test
	 public void testCalculateColumnTotal_RowCountNegative() {
		 mockingContext.checking(new Expectations() {
			 {
				 one(values).getRowCount();
				 will(returnValue(-1));
			 }
		 });
		 int[] validRows = {1, 0};
		 double result = DataUtilities.calculateColumnTotal(values, 1, validRows);
		 assertEquals("Total should be 0.0 when rowCount is negative", 0.0, result, 1e-9);
	 }
	 
	 @Test
	 public void testCalculateRowTotal_ValidColumns() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(5)); 
	             one(values).getValue(0, 1);
	             will(returnValue(10.0));
	             one(values).getValue(0, 3);
	             will(returnValue(20.0));
	         }
	     });

	     int[] validCols = {1, 3}; // Only these columns are valid
	     double result = DataUtilities.calculateRowTotal(values, 0, validCols);

	     assertEquals("Total should sum 10.0 + 20.0", 30.0, result, 1e-9);
	 }
	 
	 
	 @Test
	 public void calculateRowTotalForTwoValues() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(2));
	             one(values).getValue(1, 0);
	             will(returnValue(8.5));
	             one(values).getValue(1, 1);
	             will(returnValue(1.5));
	         }
	     });
	     double result2 = DataUtilities.calculateRowTotal(values, 1);
	    
	     assertEquals(10.0, result2, .000000001d);    
	     
	 }
	 
	 @Test
	 public void calculateRowTotalForNullValues() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(0));
	         }
	     });
	     double result2 = DataUtilities.calculateRowTotal(values, 1);
	    
	     assertEquals(0, result2, .000000001d);   
	 }
	 
	 @Test
	 public void testCalculateRowTotal_NullValueIgnored_W_VC() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(3)); 
	             one(values).getValue(0, 0);
	             will(returnValue(10.0)); //  valid
	             one(values).getValue(0, 1);
	             will(returnValue(null)); //  should be ignored
	             one(values).getValue(0, 2);
	             will(returnValue(20.0)); //  valid
	         }
	     });

	     int[] validCols = {0, 1, 2}; 
	     double result = DataUtilities.calculateRowTotal(values, 0, validCols);

	     assertEquals("Total should sum only non-null values: 10.0 + 20.0", 30.0, result, 1e-9);
	 }
	 
	 @Test
	 public void testCalculateRowTotal_NullValueIgnored_WO_VC() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(3)); 
	             one(values).getValue(0, 0);
	             will(returnValue(10.0)); //  valid
	             one(values).getValue(0, 1);
	             will(returnValue(null)); //  should be ignored
	             one(values).getValue(0, 2);
	             will(returnValue(20.0)); //  valid
	         }
	     });

	     double result = DataUtilities.calculateRowTotal(values, 0);

	     assertEquals("Total should sum only non-null values: 10.0 + 20.0", 30.0, result, 1e-9);
	 }
	 
	 @Test
	 public void testCalculateRowTotal_ColumnCountNegative() {
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(-1)); // Force colCount to be negative
	         }
	     });

	     int[] validCols = {0, 1}; // Even with valid columns, total should remain 0
	     double result = DataUtilities.calculateRowTotal(values, 0, validCols);

	     // Since colCount < 0, the total should be 0.0
	     assertEquals("Total should be 0.0 when colCount is negative", 0.0, result, 1e-9);
	 }
	 
	 
	 @Test
	 public void getCumulativePercentageWithNullValues() {
		 mockingContext.checking(new Expectations() {
			 {
				 allowing(kValues).getItemCount();
			     will(returnValue(3));

			     allowing(kValues).getValue(0);
			     will(returnValue(null));
			     allowing(kValues).getKey(0);
			     will(returnValue(0));

			     allowing(kValues).getValue(1);
			     will(returnValue(6));
			     allowing(kValues).getKey(1);
			     will(returnValue(1));

			     allowing(kValues).getValue(2);
			     will(returnValue(10));
			     allowing(kValues).getKey(2);
			     will(returnValue(2));
			 }	 
		 });
		 
		 resultValue = DataUtilities.getCumulativePercentages(kValues);
		 
		 assertEquals(0.375, resultValue.getValue(1).doubleValue(), .000000001d);
	     assertEquals(1.0, resultValue.getValue(2).doubleValue(), .000000001d);
	 }
	 
	 @Test
	 public void getCumulativePercentagesForThreeValues() {
		 mockingContext.checking(new Expectations() {
			 {
				 allowing(kValues).getItemCount();
			     will(returnValue(3));

			     allowing(kValues).getValue(0);
			     will(returnValue(5));
			     allowing(kValues).getKey(0);
			     will(returnValue(0));

			     allowing(kValues).getValue(1);
			     will(returnValue(9));
			     allowing(kValues).getKey(1);
			     will(returnValue(1));

			     allowing(kValues).getValue(2);
			     will(returnValue(2));
			     allowing(kValues).getKey(2);
			     will(returnValue(2));
			 }	 
		 });
		 
		 resultValue = DataUtilities.getCumulativePercentages(kValues);
		 
		 
		 assertEquals(0.3125, resultValue.getValue(0).doubleValue(), .000000001d);
		 assertEquals(0.875, resultValue.getValue(1).doubleValue(), .000000001d);
	     assertEquals(1.0, resultValue.getValue(2).doubleValue(), .000000001d);
	 }
	 
	 @Test
	 public void createNumberArrayWithMultipleValues() {
		 double[] array = {10,20,30};
		 
		 Number[] resultArray = DataUtilities.createNumberArray(array);
		 
		 assertEquals(array[0], resultArray[0]);
		 assertEquals(array[1], resultArray[1]);
		 assertEquals(array[2], resultArray[2]); 
	 }
	 
	 @Test
	 public void createNumberArrayWithOneValuee() {
		 double[] array = {10};
		 
		 Number[] resultArray = DataUtilities.createNumberArray(array);
		 
		 assertEquals(array[0], resultArray[0]);
	 }
	 
	 @Test
	 public void createNumberArrayWithNoValues() {
		 double[] array = {};
		 
		 Number[] resultArray = DataUtilities.createNumberArray(array);
		 
		 assertNotNull(resultArray);
	 }
	 
	 @Test
	 public void createNumberArray2DWithTwoArrays() {
		 double[][] array2D = {{10,20}, {11,22}};
		 
		 Number[][] resultArray2D = DataUtilities.createNumberArray2D(array2D);
		 
		 assertEquals(array2D[0][0], resultArray2D[0][0]);
		 assertEquals(array2D[0][1], resultArray2D[0][1]);
		 assertEquals(array2D[1][0], resultArray2D[1][0]);
		 assertEquals(array2D[1][1], resultArray2D[1][1]);
	 }
	 
	 @Test
	 public void createNumberArray2DWithEmptyArrays() {
		 double[][] array2D = {{}, {}};
		 
		 Number[][] resultArray2D = DataUtilities.createNumberArray2D(array2D);
		 
		 assertNotNull(resultArray2D);
	 }

}
