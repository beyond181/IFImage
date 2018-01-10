# IFImage
一款React  native的图片加载框架
使用方法：
1.yarn add react-native-ifimage
在工程的目录，使用yarn添加图片控件, 当然npm也行，这里推荐yarn(如果第一次失败了，那就再试一次，总能成功的)

2.react-native link react-native-ifimage
添加react-native-ifimage的关联（当然你也可以手动添加，如果你不嫌麻烦的话）

3.在js文件中引入import IFImage from 'react-native-ifimage';


4.如果需要加载gif图的话，需要在你项目的Android目录下的app下的build.gradle添加     
compile 'com.facebook.fresco:animated-gif:1.3.0'

5.好了现在就可以引用了


<IFImage
    style={{width:200, height:200, marginTop:5}}
    source={{uri:'http://f.hiphotos.baidu.com/baike/pic/item/08f790529822720e910b489078cb0a46f31fab97.jpg'}}
/>




