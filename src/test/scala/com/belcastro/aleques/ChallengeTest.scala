package com.belcastro.aleques

import com.belcastro.aleques.Challenge._
import org.junit.{Assert, Test}

import scala.collection.mutable.ListBuffer

class ChallengeTest {

  val manyArgs: Array[String] = Array("/tmp/dummy.txt","AnotherArgument")
  val noArg: Array[String] = Array.empty
  val arg: Array[String] = Array("/tmp/dummy.txt")

  @Test(expected = classOf[IllegalArgumentException])
  def noArgumentUsageTest: Unit = usage(noArg)

  @Test(expected = classOf[IllegalArgumentException])
  def usageTest: Unit = usage(arg)

  @Test(expected = classOf[IllegalArgumentException])
  def manyArgumentsUsageTest: Unit = usage(manyArgs)

  @Test
  def getHeaderTest: Unit = {
    val headerExpected = "T          S       N   RS         MinV    MaxV   " +
      "\n--------------------------------------------------"

    Assert.assertEquals(headerExpected, getHeader)
  }

  @Test
  def parseTest: Unit = {
    val line : String = "1355270609 1.80215"
    val line1: String = "1355270621 1.80185"
    val line2: String = "1355270646 1.80195"
    Assert.assertEquals((1355270609l,1.80215d), parse(line))
    Assert.assertEquals((1355270621l,1.80185d), parse(line1))
    Assert.assertEquals((1355270646l,1.80195d), parse(line2))
  }

  @Test
  def anotherTimeWindowTest: Unit = {
    val timeWindowData:List[(Long, Double)] = List((1355270580,0.0),(1355270601,0.0),(1355270639,0.0))
    timeWindowData.foreach(e => Assert.assertEquals(false, anotherTimeWindow(1355270580l, e)))
    Assert.assertEquals(true, anotherTimeWindow(1355270580l,(1355270640l,0.0)))
  }

  @Test
  def beginTimeWindowTest: Unit = {
    val timeWindowData:List[(Long, Double)] = List((1355270580,0.0),(1355270601,0.0),(1355270639,0.0))
    timeWindowData.foreach(e => Assert.assertEquals(1355270580l, beginTimeWindow(e)))
  }

  @Test
  def consolidateTest: Unit = {
    val data : ListBuffer[(Long, Double)] = ListBuffer((1355270580,1.80215), (1355270601,1.80185), (1355270639,1.80195))
    val data1: ListBuffer[(Long, Double)] = ListBuffer((1355270645,1.80225), (1355270690,1.80215))
    val data2: ListBuffer[(Long, Double)] = ListBuffer((1355270703,1.80235), (1355270704,1.80205))
    val data3: ListBuffer[(Long, Double)] = ListBuffer((1355270839,1.80225))

    val dataConsolidated : (Long, Double, Int, Double, Double, Double) = (1355270580, 1.80215, 3, 5.40595, 1.80185, 1.80215)
    val dataConsolidated1: (Long, Double, Int, Double, Double, Double) = (1355270640, 1.80225, 2, 3.60440, 1.80215, 1.80225)
    val dataConsolidated2: (Long, Double, Int, Double, Double, Double) = (1355270700, 1.80235, 2, 3.60440, 1.80205, 1.80235)
    val dataConsolidated3: (Long, Double, Int, Double, Double, Double) = (1355270820, 1.80225, 1, 1.80225, 1.80225, 1.80225)

    Assert.assertEquals(dataConsolidated , consolidate(data))
    Assert.assertEquals(dataConsolidated1, consolidate(data1))
    Assert.assertEquals(dataConsolidated2, consolidate(data2))
    Assert.assertEquals(dataConsolidated3, consolidate(data3))

  }

  @Test
  def toOutputTest: Unit = {
    val record :(Long, Double, Int, Double, Double, Double) = (1355270820,1.80235,3,5.40665,1.80205,1.80235)
    val record2:(Long, Double, Int, Double, Double, Double) = (1355271000,1.80245,2,3.6053,1.80245,1.80285)
    val record3:(Long, Double, Int, Double, Double, Double) = (1355270580,1.80215,2,3.604,1.80185,1.80215)

    val recordExpected  = "1355270820 1.80235 3   5.40665    1.80205 1.80235"
    val recordExpected2 = "1355271000 1.80245 2   3.60530    1.80245 1.80285"
    val recordExpected3 = "1355270580 1.80215 2   3.60400    1.80185 1.80215"

    Assert.assertEquals(recordExpected, toOutput(record))
    Assert.assertEquals(recordExpected2, toOutput(record2))
    Assert.assertEquals(recordExpected3, toOutput(record3))

  }

}
