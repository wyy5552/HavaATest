// Learn TypeScript:
//  - https://docs.cocos.com/creator/manual/en/scripting/typescript.html
// Learn Attribute:
//  - https://docs.cocos.com/creator/manual/en/scripting/reference/attributes.html
// Learn life-cycle callbacks:
//  - https://docs.cocos.com/creator/manual/en/scripting/life-cycle-callbacks.html

import Live2dComponent from "../components/live2d/Live2dComponent";

const {ccclass, property} = cc._decorator;

@ccclass
export default class Main extends cc.Component {

    private livePlayer:cc.Node;
    private rootCanvas:cc.Node;
    private spineList:cc.Node[] = [];
    private liveList:cc.Node[] = [];

    // onLoad () {}

    start () {
        this.rootCanvas = cc.find('Canvas/container');
    }

    addSpine(){
        let node = new cc.Node();
        let sk = node.addComponent(sp.Skeleton);
        let skins = ['spinecase/girl/mix-and-match-pro','spine/alien/alien-pro'];
        let rand = Math.floor(Math.random()*2);
        let skinUrl = skins[0];
        cc.loader.loadRes(skinUrl, sp.SkeletonData, (err, spineAsset)=> {
            if(err) {
                return;
            }
            sk.skeletonData = spineAsset!;
            if(rand === 0){
                sk.setSkin('full-skins/girl');
                sk.setAnimation(0, 'dance', true);
            }
            else{
                let ani = sk.setAnimation(0, 'run', true);
            }
            sk.premultipliedAlpha = true;
        });
        node.setScale(.5,.5);
        node.parent = this.rootCanvas;
        node.setPosition(this.rootCanvas.width*Math.random(), this.rootCanvas.height*Math.random());
        this.spineList.push(node);
        let label = cc.find('Canvas/spineLabel').getComponent(cc.Label) as cc.Label;
        label.string = this.spineList.length+'';
    }

    public delSpine(){
        if(this.spineList.length === 0)return;
        let node = this.spineList.pop();
        node.parent = null;
        node.destroy();
        let label = cc.find('Canvas/spineLabel').getComponent(cc.Label) as cc.Label;
        label.string = this.spineList.length+'';
    }

    addLive(){
        let node:cc.Node;
        if(this.livePlayer){
            node = cc.instantiate(this.livePlayer);
        }
        else{
            node = this.livePlayer = new cc.Node();
            let live = this.livePlayer.addComponent(Live2dComponent);
            live.fps = 30;
            live.scale = .5;
            // node.setScale(.5,.5);
            this.livePlayer.scaleY = -1;
            live.modelName = 'Haru';
        }
        
        node.setPosition(this.rootCanvas.width*Math.random(), this.rootCanvas.height*Math.random());
        node.parent = this.rootCanvas;
        this.liveList.push(node);
        let label = cc.find('Canvas/liveLabel').getComponent(cc.Label) as cc.Label;
        label.string = this.liveList.length+'';
    }

    delLive(){
        if(this.liveList.length === 0)return;
        let node = this.liveList.pop();
        node.parent = null;
        node.destroy();
        let label = cc.find('Canvas/liveLabel').getComponent(cc.Label) as cc.Label;
        label.string = this.liveList.length+'';
    }

    // update (dt) {}
}
