package expo.modules.articlecontextview
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ArticleContextViewModule : Module() {
  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  override fun definition() = ModuleDefinition {
    Name("ArticleContextView")

    View(ArticleContextView::class) {
      Events("onWordClick")

      Prop("wordsArray") { view: ArticleContextView, prop: List<List<String>>? ->
        prop?.let{
          view.updateWords(prop)
        }
      }

      Prop("fontSize") { view: ArticleContextView, fontSize: Double? ->
        fontSize?.let {
          view.setFontSize(it.toFloat())
        }
      }

      Prop("color") { view: ArticleContextView, color: String? ->
        color?.let {
          view.setColor(color)
        }
      }

      Prop("delay") { view: ArticleContextView, delay: Long? ->
        delay?.let {
          view.setDelay(delay)
        }
      }

      Prop("lineSpacing") { view: ArticleContextView, lineSpacing: Float? ->
        lineSpacing?.let {
          view.setLineSpacing(lineSpacing)
        }
      }

    }
  }
}
