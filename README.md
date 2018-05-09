# RecyclerView实现纵向导航栏

## 预览
![](https://github.com/mzyq/VerticalTabLayout/blob/8257a850329673d5ff1b9d1afa9ffedae307a73b/preview.gif)

## 思路
纵向的RecyclerView完全没有难度，主要是指示器（IndicatorView）的动画，下面主要说IndicatorView的实现
1. ```TranslationY```动画：由初始位置滑动到点击的当前位置
```
ObjectAnimator animator = ObjectAnimator.ofFloat(this, "TranslationY", oldY, newY);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
```
2. 获取当前位置：RecyclerView点击的时候可以获取当前点击的View，有View就可以拿到位置信息。
3. 获取初始位置，就是第0个View的位置。如果只是简单的```recyclerView.getLayoutManager().findViewByPosition(0)```，得到的View=null，尴尬不？
这是因为在获取的时候RecyclerView并没有加载完成，所以这个问题就转换成了RecyclerView什么时候加载完成。这就好说了吧。
>我的思路是在Adapter中ViewHolder，binging数据的时候做一个回调，在回调中```recyclerView.getLayoutManager().findViewByPosition(0)```并```if(view!=null)```

### Adapter代码
```
    private OnBinding onBinding;

    public void setOnBinding(OnBinding onBinding) {
        this.onBinding = onBinding;
    }

    public interface  OnBinding{
        void onBinding();
    }

    if (onBinding!=null){
            onBinding.onBinding();
        }
```

### Activity代码
```
adapter.setOnBinding(new NavAdapter.OnBinding() {
            @Override
            public void onBinding() {
                View child = manager.findViewByPosition(0);
                if (child != null) {
                    indicatorView.openAnimator(child);
                }
            }
        });
```


## Library
* 用到了Adapter框架[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
