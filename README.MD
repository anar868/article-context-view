use it as 
<ArticleContext 
    style={styles.context}
    wordsArray={[data.text, 
        [...data.keywords, ...data.unknownWords], 
        [colorScheme === "dark" ? "#ffffff" : "#000000"],
        [colorScheme === "dark" ? "#000000" : "#ffffff"],
        [colorScheme === "dark" ? "#cccccc" : "#323232"]
    ]}
    onWordClick={(event) => {onWordClick(event.nativeEvent.word)}}
    fontSize={18}
    delay={150}
    color={colorScheme === "dark" ? "#ffffff" : "#000000"}
    lineSpacing={20}
/>


might need to import import 'react-native-gesture-handler'; in the root file of thr project (App.tsx)