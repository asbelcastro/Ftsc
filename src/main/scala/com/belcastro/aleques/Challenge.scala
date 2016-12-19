package com.belcastro.aleques

import java.io.File
import java.time.{LocalDateTime, ZoneOffset}

import scala.io.Source

object Challenge {

  val SPACE = " "
  val TAB   = "\t"
  val HYPHEN = "-"

  def main(args: Array[String]): Unit = {
    usage(args)
    val dataProcessed = process(getFileContentParsed(args(0)))
    println(getHeader)
    dataProcessed.foreach(item => println(toOutput(item)))
  }

  def usage(args: Array[String]): Unit = {
    require(args.length == 1,
      """Please, provide the arguments properly.
        <TimeSerieFilePath> - Absolute path for time series file
        i.e. java -jar challenge-01.00.00.jar /tmp/data_scala.txt
      """)
  }

  def getFileContentParsed(path: String): List[(Long, Double)] = {
    val timeSeriesFile = new File(path)
    require(timeSeriesFile.exists,
      s"Path: ${timeSeriesFile.getAbsolutePath} does not exist. Please provide a valid path for a time series file.")

    Source
      .fromFile(timeSeriesFile)
      .getLines
      .map(line => {
          val columns = line.replace(TAB, SPACE).split(SPACE, -1)
          (columns(0).toLong,columns(1).toDouble)
        }).toList

  }

  def process(data: List[(Long, Double)]): Iterable[(Long, Double, Int, Double, Double, Double)] = {
    data.groupBy(timeWindow).map(toReport)
  }

  def timeWindow:((Long, Double)) => Long = {
    case (epoch, priceRatio) =>
      LocalDateTime
        .ofEpochSecond(epoch, 0, ZoneOffset.UTC)
        .withSecond(0)
        .toEpochSecond(ZoneOffset.UTC)

  }

  def toReport:((Long, List[(Long, Double)])) => (Long, Double, Int, Double, Double, Double) = {
    case (timeWindow, measurements) =>
      val firstRatio = measurements.head._2
      val initial = (timeWindow, firstRatio, measurements.size, 0.0, firstRatio, firstRatio)
      measurements.foldLeft(initial) {
        case ((tw, fpr, s, rs, min, max), measurement) =>
          val ratio = measurement._2
          (tw, fpr, s, rs + ratio, math.min(min, ratio), math.max(max, ratio))
      }
  }

  def toOutput:((Long, Double, Int, Double, Double, Double)) => String = {
    case ((timeWindow, priceRatio, num, sum, min, max)) =>
      val sumRounded = BigDecimal(sum).setScale(5, BigDecimal.RoundingMode.HALF_UP)
      f"$timeWindow$SPACE$priceRatio$SPACE$num%-3s$SPACE$sumRounded%-10s$SPACE$min$SPACE$max"
  }

  def getHeader: String = {
    val T = "T"
    val V = "S"
    val N = "N"
    val RS = "RS"
    val MIN_V = "MinV"
    val MAX_V = "MaxV"
    f"$T%-11s$V%-8s$N%-4s$RS%-11s${MIN_V}%-8s${MAX_V}%-7s%n${HYPHEN * 50}"
  }

}
