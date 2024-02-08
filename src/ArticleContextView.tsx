import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';
import { ViewProps } from 'react-native';

export type onWordClick = {
	word : string;
};

export type Props = {
	wordsArray?: string[][];
	onWordClick?: (event: {nativeEvent: onWordClick}) => void;
	fontSize?: number;
	color?: string;
	delay?: number;
	lineSpacing?: number;
} & ViewProps;

const NativeView: React.ComponentType<Props> =
	requireNativeViewManager('ArticleContextView');

export default function ArticleContextView(props: Props) {
	return <NativeView {...props} />;
}
