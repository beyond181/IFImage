# IFImage
一款React  native的图片加载框架
使用方法：
1.yarn add react-native-ifimage
在工程的目录，使用yarn添加图片控件, 当然npm也行，这里推荐yarn(如果第一次失败了，那就再试一次，总能成功的)

2.react-native link react-native-ifimage
添加react-native-ifimage的关联（当然你也可以手动添加，如果你不嫌麻烦的话）

3.在js文件中引入import IFImage from 'react-native-ifimage';

4.好了现在就可以引用了
<IFImage
              style={{width:200, height:200, marginTop:5}}
              source={{uri:'http://f.hiphotos.baidu.com/baike/pic/item/08f790529822720e910b489078cb0a46f31fab97.jpg'}}
          />
注意如果需要加载gif图的话，需要在你项目的Android目录下的app下的build.gradle添加     
compile 'com.facebook.fresco:animated-gif:1.3.0'
下面来说一下IFImage提供的全部属性：
<IFImage
    style={{width: 200, height: 200, borderWidth: 10, borderColor: 'gray', borderRadius:10, margin:10}}//这里边设置正常属性，包括边框的样式
    focusStyle={{borderWidth: 10, borderColor: 'green', borderRadius:8}}//获得焦点时边框的样式
    selectedStyle={{borderWidth: 10, borderColor: 'yellow', borderRadius:6}}//选中时边框的样式
    source={{uri: this.props.uri}}
    scale={2.0} //获取焦点有个默认放大动画，这里设置放大的倍数
    interceptDirection={'up'}//要屏蔽的方向键
    circle={false}//是否是圆形图，设置了此属性就没必要设置圆角了
    autoPlay={true}//gif图是否自动播放，只有gif图的时候才设置改属性
    scaleType={'centerCrop'}//太多了，下面说明
    onKeyEvents={({comView, direction}) => {//接收遥控器的按键，direction分别是up, left, down, right

    }}
    focusAnim={true}//获得焦点时是否触发动画，默认是false
    onFocus={({comView，hasF}) => {//焦点改变时走改方法 hasF为true时为有焦点，为false时失去焦点

    }
    }
    onClick={({comView}) => {//点击时走该方法
        this.state.selectFlag = !this.state.selectFlag;//这里是我自己定义的state，一个bool值
        this._imageif.setSelectedBorder(this.state.selectFlag);
        //this._imageif.setFocusBorder(true);//设置控件显示成获得焦点时的边框
        //this._imageif.setChangeFocused(true);//给该控件焦点，或者让该控件失去焦点
    }}/>
