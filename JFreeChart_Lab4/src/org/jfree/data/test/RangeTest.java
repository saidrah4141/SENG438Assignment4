package org.jfree.data.test;

import static org.junit.Assert.*; 
import org.jfree.data.Range; 
import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }


    @Before
    public void setUp() throws Exception { exampleRange = new Range(-1, 1);
    }


    @Test
    public void testConstrain() {
        // Test Strategy: Equivalence Partitioning - Normal Value
        // Description: Tests the constrain method with a value within the range.
        // Input: value = 6.0 (within the range [5.0, 10.0])
        // Expected Output: 6.0
        Range range1 = new Range(5.0, 10.0);
        assertEquals("The value should just return itself", 6.0, range1.constrain(6.0), 1e-9);
    }
    @Test
    public void testConstrain_ValueWithinRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(3.0, range.constrain(3.0), 0.0001);
    }
    @Test
    public void testConstrain_ValueAtLowerBound() {
        Range range = new Range(1.0, 5.0);
        assertEquals(1.0, range.constrain(1.0), 0.0001);
    }
  
    @Test
    public void testConstrain_LessThanLowerBound() {
        // Test Strategy: Boundary Value Analysis - Below Lower Bound
        // Description: Tests the constrain method with a value less than the lower bound.
        // Input: value = 1.0 (less than the lower bound 5.0) z
        // Expected Output: 5.0 (lower bound)
        Range range1 = new Range(5.0, 10.0);
        assertEquals("The value less than the lower bound should return the lower bound", 5.0, range1.constrain(1.0), 1e-9);
    }

    @Test
    public void testConstrain_GreaterThanUpperBound() {
        // Test Strategy: Boundary Value Analysis - Above Upper Bound
        // Description: Tests the constrain method with a value greater than the upper bound.
        // Input: value = 11.0 (greater than the upper bound 10.0)
        // Expected Output: 10.0 (upper bound)
        Range range1 = new Range(5.0, 10.0);
        assertEquals("The value greater than the upper bound should return the upper bound", 10.0, range1.constrain(11.0), 1e-9);
    }

    @Test
    public void testContains_WithinRange() {
        // Test Strategy: Equivalence Partitioning - Value Within Range
        // Description: Tests the contains method with a value within the range.
        // Input: value = 6.0 (within the range [5.0, 10.0])
        // Expected Output: true
        Range range1 = new Range(5.0, 10.0);
        assertTrue("Should return true because value is within range", range1.contains(6.0));
    }
    @Test
    public void testContains_AtLowerBoundary() {
        // Test Strategy: Equivalence Partitioning - Value Within Range
        // Description: Tests the contains method with a value within the range.
        // Input: value = 5.0 (within the range [5.0, 10.0])
        // Expected Output: true
        Range range1 = new Range(5.0, 10.0);
        assertTrue("Should return true because value is within range", range1.contains(5.0));
    }
    @Test
    public void testContains_AtUpperBoundary() {
        // Test Strategy: Equivalence Partitioning - Value Within Range
        // Description: Tests the contains method with a value within the range.
        // Input: value = 10.0 (within the range [5.0, 10.0])
        // Expected Output: true
        Range range1 = new Range(5.0, 10.0);
        assertTrue("Should return true because value is within range", range1.contains(10.0));
    }

    @Test
    public void testContains_OutsideRange() {
        // Test Strategy: Equivalence Partitioning - Value Outside Range
        // Description: Tests the contains method with a value outside the range.
        // Input: value = 11.0 (outside the range [5.0, 10.0])
        // Expected Output: false
        Range range1 = new Range(5.0, 10.0);
        assertFalse("Should return false because value is outside range", range1.contains(11.0));
    }

    @Test
    public void testContains_NegativeRange() {
        Range range = new Range(-1.0, 1.0);
        assertTrue(range.contains(0.0));  // Ensures bounds are correct
    }
    
    @Test
    public void testContains_SinglePointRange() {
        Range range = new Range(5.0, 5.0);
        assertTrue(range.contains(5.0));  // Must return true
        assertFalse(range.contains(5.1)); // Must return false
    }
    
    @Test
    public void testContains_JustBelowLowerBound() {
        Range range = new Range(5.0, 10.0);
        assertFalse(range.contains(4.999)); // Should return false
    }

    @Test
    public void testContains_JustAboveUpperBound() {
        Range range = new Range(5.0, 10.0);
        assertFalse(range.contains(10.001)); // Should return false
    }
    @Test
    public void testContains_OutsideButSatisfiesOneCondition() {
        Range range = new Range(5.0, 10.0);
        assertFalse(range.contains(4.0));  // Below lower (should return false)
        assertFalse(range.contains(11.0)); // Above upper (should return false)
    }

    
	    @Test
	    public void testGetLowerBound() {
	        // Test Strategy: Equivalence Partitioning - Valid Range
	        // Description: Tests the getLowerBound method to ensure it returns the correct lower bound of the range.
	        // Input: range = [1.0, 5.0]
	        // Expected Output: 1.0 (the lower bound of the range)
	    	Range range1=new Range(1.0,5.0);
	    	
	    	assertEquals("The lowerbound should be 1.0",1,range1.getLowerBound(),.000000001d);
	    
	    }

    @Test
    public void testExpandToInclude_NullRange() {
        // Test expanding a null range
        Range result = Range.expandToInclude(null, 5.0);
        Range expected = new Range(5.0, 5.0);
        assertEquals("Expanding a null range should return a new range with the value as both bounds", expected, result);
    }

    @Test
    public void testExpandToInclude_ValueBelowLowerBound() {
      
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, 1.0);
        Range expected = new Range(1.0, 6.0);
        assertEquals("Expanding to include a value below the lower bound should return a new range with the value as the lower bound", expected, result);
    }

    @Test
    public void testExpandToInclude_ValueAboveUpperBound() {
        // Test expanding a range to include a value above the upper bound
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, 7.0);
        Range expected = new Range(2.0, 7.0);
        assertEquals("Expanding to include a value above the upper bound should return a new range with the value as the upper bound", expected, result);
    }

    @Test
    public void testExpandToInclude_ValueWithinRange() {
        // Test expanding a range to include a value already within the range
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, 4.0);
        Range expected = new Range(2.0, 6.0);
        assertEquals("Expanding to include a value already within the range should return the original range", expected, result);
    }
    @Test
    public void testExpandToInclude_ValueEqualsLowerBound() {
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, 2.0);
        assertEquals("Value equal to lower bound should not expand range", range, result);
    }
    @Test
    public void testExpandToInclude_ValueEqualsUpperBound() {
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, 6.0);
        assertEquals("Value equal to upper bound should not expand range", range, result);
    }
    @Test
    public void testExpandToInclude_ValueIsNaN() {
        Range range = new Range(2.0, 6.0);
        Range result = Range.expandToInclude(range, Double.NaN);
        assertEquals("NaN value should not expand range", range, result);
    }
    @Test
    public void testExpandToInclude_NegativeRange() {
        Range range = new Range(-5.0, -1.0);
        Range result = Range.expandToInclude(range, -6.0);
        Range expected = new Range(-6.0, -1.0);
        assertEquals("Negative range should expand correctly", expected, result);
    }

    @Test
    public void testExpandToInclude_ZeroValue() {
        Range range = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(range, 0.0);
        Range expected = new Range(0.0, 5.0);
        assertEquals("Zero value should expand lower bound", expected, result);
    }@Test
    public void testExpandToInclude_Infinity() {
        Range range = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(range, Double.POSITIVE_INFINITY);
        Range expected = new Range(1.0, Double.POSITIVE_INFINITY);
        assertEquals("Infinity should expand upper bound", expected, result);
    }
    @Test
    public void testGetLength() {
        // Test Strategy: Equivalence Partitioning - Valid Range
        // Description: Tests the getLength method with a valid range.
        // Input: range = [1.0, 5.0]
        // Expected Output: Length = 4.0
        Range range1 = new Range(1.0, 5.0);
        assertEquals("The length of the range should be 4", 4, range1.getLength(), .000000001d);
    }
 
    @Test
    public void testGetLowerBound_ValidRange() {
     
        Range range = new Range(1.0, 5.0);
        assertEquals("The lower bound should be 1.0", 1.0, range.getLowerBound(), 0.000000001d);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetLowerBound_InvalidRange() {

        Range range = new Range(10.0, 5.0);
        
        range.getLowerBound();
    }
    
    @Test
    public void testGetUpperBound_ValidRange() {
        
        Range range = new Range(1.0, 5.0);
        assertEquals("The upper bound should be 5.0", 5.0, range.getUpperBound(), 0.000000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUpperBound_InvalidRange() {
       
        Range range = new Range(10.0, 5.0);
       
        range.getUpperBound();
    }
    
    @Test
    public void testGetCentralValue_BasicRange() {
        Range range = new Range(0.0, 4.0);
        assertEquals(2.0, range.getCentralValue(), 0.0001);
    }
    @Test
    public void testGetCentralValue_NegativeRange() {
        Range range = new Range(-4.0, 0.0);
        assertEquals(-2.0, range.getCentralValue(), 0.0001);
    }
    @Test
    public void testGetCentralValue_MixedRange() {
        Range range = new Range(-2.0, 2.0);
        assertEquals(0.0, range.getCentralValue(), 0.0001);
    }
    @Test
    public void testGetCentralValue_SinglePoint() {
        Range range = new Range(3.0, 3.0);
        assertEquals(3.0, range.getCentralValue(), 0.0001);
    }
    
    @Test
    public void testIntersects_Branch1_Intersects() {
       
        Range range = new Range(5.0, 10.0);
        assertTrue("The ranges should intersect", range.intersects(3.0, 6.0));
    }

    @Test
    public void testIntersects_Branch1_NoIntersection() {
       
        Range range = new Range(5.0, 10.0);
        assertFalse("The ranges should not intersect", range.intersects(3.0, 4.0));
    }

    @Test
    public void testIntersects_Branch2_Intersects() {
       
        Range range = new Range(5.0, 10.0);
        assertTrue("The ranges should intersect", range.intersects(6.0, 8.0));
    }

    @Test
    public void testIntersects_Branch2_NoIntersection() {
        
        Range range = new Range(5.0, 10.0);
        assertFalse("The ranges should not intersect", range.intersects(11.0, 12.0));
    }
    
    @Test
    public void testIntersects_Branch1_ExactLowerBound() {
        Range range = new Range(5.0, 10.0);
        assertTrue("Should intersect since b1 > lower", range.intersects(5.0, 6.0));
    }
    @Test
    public void testIntersects_Branch1_TouchingLowerBound() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Should not intersect since b1 == lower", range.intersects(3.0, 5.0));
    }
    @Test
    public void testIntersects_Branch2_ExactUpperBound() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Should not intersect since b0 == upper", range.intersects(10.0, 12.0));
    }
    @Test
    public void testIntersects_Branch2_ZeroWidthRange() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Zero-width range outside should not intersect", range.intersects(11.0, 11.0));
        assertTrue("Zero-width range inside should intersect", range.intersects(6.0, 6.0));
    }	
    
 
    @Test
    public void testCombine_BothRangesNull() {
       
        Range result = Range.combine(null, null);
        assertNull("Combining two null ranges should return null", result);
    }

    @Test
    public void testCombine_Range1Null() {
      
        Range range2 = new Range(2.0, 5.0);
        Range result = Range.combine(null, range2);
        assertEquals("Combining null with range2 should return range2", range2, result);
    }

    @Test
    public void testCombine_Range2Null() {
      
        Range range1 = new Range(1.0, 3.0);
        Range result = Range.combine(range1, null);
        assertEquals("Combining range1 with null should return range1", range1, result);
    }

    @Test
    public void testCombine_BothRangesValid() {
        
        Range range1 = new Range(1.0, 3.0);
        Range range2 = new Range(2.0, 5.0);
        Range expected = new Range(1.0, 5.0); // Lower bound from range1, upper bound from range2
        Range result = Range.combine(range1, range2);
        assertEquals("Combining two valid ranges should return a new range with correct bounds", expected, result);
    }
    @Test
    public void testCombineIgnoringNaN_BothRangesNull() {
      
        Range result = Range.combineIgnoringNaN(null, null);
        assertNull("Combining two null ranges should return null", result);
    }

    @Test
    public void testCombineIgnoringNaN_Range1Null_Range2Valid() {
       
        Range range2 = new Range(2.0, 5.0);
        Range result = Range.combineIgnoringNaN(null, range2);
        assertEquals("Combining null with a valid range should return the valid range", range2, result);
    }
    @Test
    public void testCombine_FirstRangeNull() {
        Range range2 = new Range(2.0, 4.0);
        Range result = Range.combine(null, range2);
        assertEquals(range2, result);
    }

    @Test
    public void testCombine_SecondRangeNull() {
        Range range1 = new Range(1.0, 3.0);
        Range result = Range.combine(range1, null);
        assertEquals(range1, result);
    }

 

    @Test
    public void testCombineIgnoringNaN_Range1Null_Range2NaN() {
  
        Range range2 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(null, range2);
        assertNull("Combining null with a NaN range should return null", result);
    }

    @Test
    public void testCombineIgnoringNaN_Range2Null_Range1Valid() {
       
        Range range1 = new Range(1.0, 3.0);
        Range result = Range.combineIgnoringNaN(range1, null);
        assertEquals("Combining a valid range with null should return the valid range", range1, result);
    }

    @Test
    public void testCombineIgnoringNaN_Range2Null_Range1NaN() {
        
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(range1, null);
        assertNull("Combining a NaN range with null should return null", result);
    }

    @Test
    public void testCombineIgnoringNaN_BothRangesValid() {
    
        Range range1 = new Range(1.0, 3.0);
        Range range2 = new Range(2.0, 5.0);
        Range expected = new Range(1.0, 5.0); // Lower bound from range1, upper bound from range2
        Range result = Range.combineIgnoringNaN(range1, range2);
        assertEquals("Combining two valid ranges should return a new range with correct bounds", expected, result);
    }

    @Test
    public void testCombineIgnoringNaN_BothRangesNaN() {
        
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range range2 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(range1, range2);
        assertNull("Combining two NaN ranges should return null", result);
    }

    @Test
    public void testCombineIgnoringNaN_OneRangeValid_OneRangeNaN() {
  
        Range range1 = new Range(1.0, 3.0);
        Range range2 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(range1, range2);
        assertEquals("Combining a valid range with a NaN range should return the valid range", range1, result);
    }
    
    @Test
    public void testCombine_SinglePointRanges() {
        Range range1 = new Range(3.0, 3.0);
        Range range2 = new Range(5.0, 5.0);
        Range result = Range.combine(range1, range2);
        assertEquals(new Range(3.0, 5.0), result);
    }

    @Test
    public void testExpand_ValidExpansion() {
      
        Range range = new Range(2.0, 6.0);
        Range result = Range.expand(range, 0.25, 0.5);
        Range expected = new Range(1.0, 8.0);
        assertEquals("The range should be expanded correctly", expected, result);
    }

    @Test
    public void testExpand_ZeroMargins() {
       
        Range range = new Range(2.0, 6.0);
        Range result = Range.expand(range, 0.0, 0.0);
        Range expected = new Range(2.0, 6.0);
        assertEquals("The range should remain unchanged", expected, result);
    }

    @Test
    public void testExpand_NegativeMargins() {
   
        Range range = new Range(2.0, 6.0);
        Range result = Range.expand(range, -0.25, -0.5);
        Range expected = new Range(3.0, 4.0);
        assertEquals("The range should be contracted correctly", expected, result);
    }

    @Test
    public void testExpand_OverlappingBounds() {
    
        Range range = new Range(2.0, 6.0);
        Range result = Range.expand(range, 1.0, 1.0);
        Range expected = new Range(-2.0, 10.0);
        assertEquals("The range should be expanded correctly", expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpand_NullRange() {
       
        Range.expand(null, 0.25, 0.5);
    }
    
    @Test
    public void testExpand_OverExpansion() {
        Range range = new Range(4.0, 6.0);
        Range result = Range.expand(range, 1.5, 1.5);
      
        Range range2 = new Range(4.0, 4.0);
        Range result2 = Range.expand(range2, 1.0, 1.0);
 
        assertEquals(new Range(4.0, 4.0), result2);
    }
    @Test
    public void testShift_ZeroCrossingAllowed() {
        
        Range base = new Range(2.0, 6.0);
        Range result = Range.shift(base, 3.0, true);
        Range expected = new Range(5.0, 9.0);
        assertEquals("The range should be shifted correctly with zero crossing allowed", expected, result);
    }

    @Test
    public void testShift_ZeroCrossingNotAllowed_NoZeroCrossing() {
       
        Range base = new Range(2.0, 6.0);
        Range result = Range.shift(base, 3.0, false);
        Range expected = new Range(5.0, 9.0);
        assertEquals("The range should be shifted correctly without zero crossing", expected, result);
    }

    @Test
    public void testShift_ZeroCrossingNotAllowed_ZeroCrossing() {
     
        Range base = new Range(-2.0, 2.0);
        Range result = Range.shift(base, -3.0, false);
        Range expected = new Range(-5.0, 0.0);
        assertEquals("The range should be shifted correctly with zero crossing adjustment", expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShift_NullBaseRange() {
        
        Range.shift(null, 3.0, true);
    }
    @Test
    public void testScale_PositiveFactor_GreaterThanOne() {
        Range base = new Range(2.0, 6.0);
        Range result = Range.scale(base, 2.0);
        assertEquals(new Range(4.0, 12.0), result);
    }

    @Test
    public void testScale_PositiveFactor_LessThanOne() {
        Range base = new Range(4.0, 8.0);
        Range result = Range.scale(base, 0.5);
        assertEquals(new Range(2.0, 4.0), result);
    }
    @Test
    public void testScale_FactorEqualsOne() {
        Range base = new Range(3.0, 5.0);
        Range result = Range.scale(base, 1.0);
        assertEquals(base, result);
    }
    @Test
    public void testScale_SinglePointRange() {
        Range base = new Range(5.0, 5.0);
        Range result = Range.scale(base, 3.0);
        assertEquals(new Range(15.0, 15.0), result);
    }@Test
    public void testScale_FactorEqualsZero() {
        Range base = new Range(2.0, 6.0);
        Range result = Range.scale(base, 0.0);
        assertEquals(new Range(0.0, 0.0), result);
    }
    
    @Test
    public void testScale_NaNInRange() {
        Range base = new Range(1.0, Double.NaN);
        Range result = Range.scale(base, 2.0);
        assertTrue(Double.isNaN(result.getUpperBound()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testScale_NegativeFactor() {
     
        Range base = new Range(2.0, 6.0);
        Range.scale(base, -1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScale_NullBaseRange() {
        
        Range.scale(null, 2.0);
    }
    
    @Test
    public void testIsNaNRange_BothBoundsNaN() {
      
        Range range = new Range(Double.NaN, Double.NaN);
        assertTrue("The range should be a NaN range", range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_LowerBoundNaN() {
  
        Range range = new Range(Double.NaN, 5.0);
        assertFalse("The range should not be a NaN range", range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_UpperBoundNaN() {
     
        Range range = new Range(2.0, Double.NaN);
        assertFalse("The range should not be a NaN range", range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_NeitherBoundNaN() {
     
        Range range = new Range(2.0, 5.0);
        assertFalse("The range should not be a NaN range", range.isNaNRange());
    }
    @Test
    public void testHashCode_ValidRange() {
     
        Range range = new Range(2.0, 6.0);
        int hashCode = range.hashCode();
        assertNotNull("The hash code should not be null", hashCode);
    }
    @Test
    public void testHashCode_EqualRanges() {
        Range range1 = new Range(1.5, 3.5);
        Range range2 = new Range(1.5, 3.5);
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testHashCode_UnequalRanges() {
        Range range1 = new Range(1.0, 2.0);
        Range range2 = new Range(1.1, 2.0);
        assertNotEquals(range1.hashCode(), range2.hashCode());
    }@Test
    public void testHashCode_ZeroRange() {
        Range range1 = new Range(0.0, 0.0);
        Range range2 = new Range(-0.0, -0.0);
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testHashCode_NaNValues() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range range2 = new Range(Double.NaN, Double.NaN);
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testHashCode_InfinityValues() {
        Range range1 = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        Range range2 = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertEquals(range1.hashCode(), range2.hashCode());
    }@Test
    public void testHashCode_FormulaVerification() {
        Range range = new Range(1.5, 3.5);
        
        long lowerBits = Double.doubleToLongBits(1.5);
        int lowerHash = (int)(lowerBits ^ (lowerBits >>> 32));
        
        long upperBits = Double.doubleToLongBits(3.5);
        int upperHash = (int)(upperBits ^ (upperBits >>> 32));
        
        int expected = 29 * lowerHash + upperHash;
        
        assertEquals(expected, range.hashCode());
    }@Test
    public void testHashCode_Consistency() {
        Range range = new Range(2.0, 4.0);
        int initialHash = range.hashCode();
        
        
        assertEquals(initialHash, range.hashCode());
        assertEquals(initialHash, range.hashCode());
    }
    
    
    
    @Test
    public void testToString_ValidRange() {
  
        Range range = new Range(2.0, 6.0);
        String expected = "Range[2.0,6.0]";
        assertEquals("The string representation should match", expected, range.toString());
    }
    @Test
    public void testToString_BasicRange() {
        Range range = new Range(1.5, 3.5);
        assertEquals("Range[1.5,3.5]", range.toString());
    }
    @Test
    public void testToString_IntegerValues() {
        Range range = new Range(1.0, 3.0);
        assertEquals("Range[1.0,3.0]", range.toString());
    }
    @Test
    public void testToString_NegativeValues() {
        Range range = new Range(-2.5, -1.0);
        assertEquals("Range[-2.5,-1.0]", range.toString());
    }
    @Test
    public void testToString_ZeroValues() {
        Range range = new Range(0.0, 0.0);
        assertEquals("Range[0.0,0.0]", range.toString());
    }

    @Test
    public void testToString_NaNValues() {
        Range range = new Range(Double.NaN, Double.NaN);
        assertEquals("Range[NaN,NaN]", range.toString());
    }

    @Test
    public void testToString_InfinityValues() {
        Range range = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertEquals("Range[-Infinity,Infinity]", range.toString());
    }
    
    
    
    
    @Test
    public void testEquals_EqualRanges() {
       
        Range range1 = new Range(2.0, 6.0);
        Range range2 = new Range(2.0, 6.0);
        assertTrue("The ranges should be equal", range1.equals(range2));
    }

    @Test
    public void testEquals_DifferentRanges() {
        
        Range range1 = new Range(2.0, 7.0);
        Range range2 = new Range(3.0, 6.0);
        assertFalse("The ranges should not be equal", range1.equals(range2));
    }

    @Test
    public void testEquals_NullInput() {
     
        Range range1 = new Range(2.0, 6.0);
        assertFalse("The range should not be equal to null", range1.equals(null));
    }

    @Test
    public void testEquals_NonRangeObject() {
      
        Range range1 = new Range(2.0, 6.0);
        String obj = "Not a Range";
        assertFalse("The range should not be equal to a non-Range object", range1.equals(obj));
    }

    @Test
    public void testEquals_SelfComparison() {
     
        Range range1 = new Range(2.0, 6.0);
        assertTrue("The range should be equal to itself", range1.equals(range1));
    }
    @Test
    public void testConstructor_ValidRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(1.0, range.getLowerBound(), 0.0001);
        assertEquals(5.0, range.getUpperBound(), 0.0001);
    }
    @Test
    public void testConstructor_EqualBounds() {
        Range range = new Range(3.0, 3.0);
        assertEquals(3.0, range.getLowerBound(), 0.0001);
        assertEquals(3.0, range.getUpperBound(), 0.0001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidRange() {
        new Range(5.0, 1.0);
    }

    @Test
    public void testConstructor_InvalidRangeMessage() {
        try {
            new Range(5.0, 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("require lower (5.0) <= upper (1.0)"));
        }
    }
    @Test
    public void testConstructor_ZeroValues() {
        Range range = new Range(0.0, 0.0);
        assertEquals(0.0, range.getLowerBound(), 0.0001);
        assertEquals(0.0, range.getUpperBound(), 0.0001);
    }

    @Test
    public void testConstructor_NegativeZero() {
        Range range = new Range(-0.0, -0.0);
        assertEquals(0.0, range.getLowerBound(), 0.0001);
        assertEquals(0.0, range.getUpperBound(), 0.0001);
    }
    

   
  
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}