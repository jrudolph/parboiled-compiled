package org.parboiled.compiler

import collection.mutable.Map

import org.parboiled.parserunners.BasicParseRunner
import org.parboiled.scala.rules.Rule1
import org.parboiled.scala.{ParsingResult, Input}
import org.parboiled.matchers.Matcher
import org.parboiled.{MatchHandler, MatcherContext}

class CompiledParseRunner[V](rule: Rule1[V]) extends BasicParseRunner[V](rule) {
  type MatchFunc = MatcherContext[_] => Boolean

  val compiledSnippets: Map[Matcher, MatchFunc] = Map.empty

  def compiled(m: Matcher): MatchFunc =
    compiledSnippets.getOrElseUpdate(m, compile(m))

  def run(input: Input): ParsingResult[V] = ParsingResult[V](run(input.inputBuffer))

  override def runRootContext: Boolean = {
    runRootContext(MyHandler, true)
  }

  object MyHandler extends MatchHandler {
    def matchRoot(rootContext: MatcherContext[_]) = {
      rootContext.runMatcher
    }
    def `match`(context: MatcherContext[_]): Boolean =
      compiled(context.getMatcher)(context)
  }

  def compile(m: Matcher): MatchFunc =
    ctx => m.`match`(ctx)
}

object CompiledParseRunner {
  def apply[V](rule: Rule1[V]) = new CompiledParseRunner[V](rule)
}