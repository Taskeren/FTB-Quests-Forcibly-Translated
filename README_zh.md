# FTB Quests: 强制翻译

[英文](README.md)

大部分模组包并没有提供一个*可以翻译*的任务书，使得译者必须修改任务书来实现翻译。

这个模组提供了一个运行时对任务书GUI的非破坏性的注入，把不能翻译的替换成可以翻译的组件。

## 如何使用

模组生成的翻译键遵循 [FTB-Quests-Localizer](https://github.com/Litchiiiiii/FTB-Quests-Localizer) 的样式（可以在配置文件中简单配置实现修改）。

```
[pattern]
	#The pattern of translation keys of Quest Titles (format: chapter filename, n-th quest start at 1)
	questTitle = "ftbquests.chapter.%s.quest%s.title"
	#The pattern of translation keys of Quest Subtitles (format: chapter filename, n-th quest of the chapter start at 1)
	questSubtitle = "ftbquests.chapter.%s.quest%s.subtitle"
	#The pattern of translation keys of Quest Descriptions (format: chapter filename, n-th quest of the chapter start at 1, n-th line of descriptions start at 1)
	questDesc = "ftbquests.chapter.%s.quest%s.description%s"
	#The pattern of translation keys of Chapter Titles (format: chapter filename)
	chapterTitle = "ftbquests.chapter.%s.title"
	#The pattern of translation keys of Chapter Group Titles (format: chapter group id)
	chapterGroupTitle = "ftbquests.chapter_groups_%s.title"
```

里面的 `%s` 会被替换成对应的内容。

默认不会为任务描述空行生成翻译键，你可以修改设置让它生成。

```
[misc]
	#Whether generate translation keys for empty quest descriptions.
	generateKeysForEmptyDescription = false
```
