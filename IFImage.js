/**
 * Created by zhiyi014 on 2017/9/25.
 */
'use strict';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import {NativeModules, requireNativeComponent, View} from 'react-native';
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';
const ReactNativePropRegistry = require('react-native/Libraries/Renderer/shims/ReactNativePropRegistry');
export default class IFImage extends Component {
    constructor(props) {
        super(props);
    }
    setNativeProps(nativeProps) {
        this._root.setNativeProps(nativeProps);
    }

    setChangeFocused = (changeF) => {
        this.setNativeProps({ changeFocused: changeF });
    };

    setFocusBorder = (focusB) => {
        this.setNativeProps({ focusBorder: focusB });
    };

    setSelectedBorder = (selectedB) => {
        this.setNativeProps({ selectedBorder: selectedB });
    };

    _assignRoot = (component) => {
        this._root = component;
    };
    getStyles = (styleProT,style)=>{
        if((typeof style == "number")){
            Object.assign(styleProT, ReactNativePropRegistry.getByID(style))
        }else if(style instanceof Array){
            for(let item of style){
                if( typeof item  == 'number'){
                    let i ;
                    i = ReactNativePropRegistry.getByID(item);
                    Object.assign(styleProT, i)
                }
                else{
                    Object.assign(styleProT, item)
                }
            }
        }else{
            Object.assign(styleProT,style)
        }
        return styleProT;
    }

    _onChangeDeal =(event)=>{
        if (event.nativeEvent.click) {
            if(this.props.onClick) {
                this.props.onClick({comView:this});
            }
        } else if (typeof event.nativeEvent.hasFocus == 'boolean') {
            if(this.props.onFocus) {
                this.props.onFocus({comView:this, hasF:event.nativeEvent.hasFocus})
            }
        }else if(event.nativeEvent.direction){
            if(this.props.onKeyEvents){
                this.props.onKeyEvents({comView:this, direction:event.nativeEvent.direction})
            }
        }
    }

    render() {
        const source = resolveAssetSource(this.props.source) || {};
        let uri = source.uri || '';
        if (uri && uri.match(/^\//)) {
            uri = `file://${uri}`;
        }
        const isNetwork = !!(uri && uri.match(/^https?:/));
        const isAsset = !!(uri && uri.match(/^(assets-library|file|content|ms-appx|ms-appdata):/));
        const nativeProps = Object.assign({}, this.props);



        let cornersRadius = 0;
        // if(this.props.cornersRadius){
        //     cornersRadius = this.props.cornersRadius
        // }
        let isCircle = false;
        if(this.props.circle){
            isCircle = this.props.circle;
        }
        let scaleType ='fit_center';
        if(this.props.scaleType){
            scaleType = this.props.scaleType;
        }
        let autoPlay = false;
        if(this.props.autoPlay){
            autoPlay = this.props.autoPlay;
        }

        let stylePro = {
            width:0,
            height:0,
            borderWidth:0,
            borderColor:'gray',
            borderRadius:0,
        };
        if(this.props.style){
            stylePro = this.getStyles(stylePro,this.props.style);
        }
        let styleF={
            borderWidthF:0,
            borderColorF:'gray',
            borderRadiusF:0
        }

        if(this.props.focusStyle) {
            let cstyle = {};
            cstyle = this.getStyles(cstyle,this.props.focusStyle);
            for (let key of Object.keys(cstyle)) {
                styleF[key+'F'] = cstyle[key];
            }
        }

        let styleS = {
            borderWidthS : 0,
            borderColorS:'gray',
            borderRadiusS: 0,
        };
        if(this.props.selectedStyle) {
            let styleT={};
            this.getStyles(styleT,this.props.selectedStyle);
            for (let key of Object.keys(styleT)) {
                styleS[key+'S'] = styleT[key];
            }
        }
        Object.assign(nativeProps, {
            src: {
                isCircle,
                scaleType,
                uri,
                autoPlay,
                ...stylePro,
            },
            selectedS:{...styleS},
            focusS:{...styleF},
            onChange : this._onChangeDeal,
            // autoPlay: this.props.autoPlay,
            // scale: this.props.scale,

        });
        return (<RCTIFImageView
            ref={this._assignRoot}
            {...nativeProps}
        />)
    }
}
IFImage.propTypes = {
    src:PropTypes.object,
    selectedS:PropTypes.object,
    focusS:PropTypes.object,
    borderWidth: PropTypes.number,
    borderColor: PropTypes.string,
    scaleType: PropTypes.string,
    circle: PropTypes.bool,

    autoPlay: PropTypes.bool,
    scale: PropTypes.number,
    onClick: PropTypes.func,
    onFocus: PropTypes.func,
    onKeyEvents: PropTypes.func,

    focusAnim: PropTypes.bool,
    canFocused: PropTypes.bool,
    interceptDirection: PropTypes.string,
    focusStyle:View.propTypes.style,
    selectedStyle:View.propTypes.style,

    changeFocused: PropTypes.bool,
    focusBorder: PropTypes.bool,
    selectedBorder: PropTypes.bool,
    /* Wrapper component */
    source: PropTypes.oneOfType([
        PropTypes.shape({
            uri: PropTypes.string
        }),
        PropTypes.number
    ]),
    ...View.propTypes, // 包含默认的View的属性

}
const RCTIFImageView = requireNativeComponent('RCTIFImageView', IFImage, {
    nativeOnly: {
        onChange: true,
        changeFocused: true,
        focusBorder: true,
        selectedBorder: true,
    }
});
