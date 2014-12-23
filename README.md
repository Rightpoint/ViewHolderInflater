[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ViewHolderInflater-lightgrey.svg?style=flat)](https://android-arsenal.com/details/1/1257) [![Raizlabs Repository](http://img.shields.io/badge/Raizlabs%20Repository-1.0.1-blue.svg?style=flat)](https://github.com/Raizlabs/maven-releases)

ViewHolderInflater
==================

The fastest view-injection Android library that populates View Holders. Using **annotation processing**,
this library generates ```findViewById()```, saves code by connecting view-related methods
to your views without need to ```findViewById``` and then ```setOnClickListener```, and some other smart
features that make it amazingly easy to use.

# Getting Started

Add the maven repo url to your buildscript in the top-level build.gradle:

```groovy

buildscript {
  repositories {
        maven { url "https://raw.github.com/Raizlabs/maven-releases/master/releases" }
  }
}

```

Add the library to the project-level build.gradle, using the [apt plugin](https://bitbucket.org/hvisser/android-apt)  and the 
[AARLinkSources](https://github.com/xujiaao/AARLinkSources) plugin:

```groovy

  dependencies {
    apt 'com.raizlabs.android:ViewHolderInflater-Compiler:1.0.1'
    aarLinkSources 'com.raizlabs.android:ViewHolderInflater-Compiler:1.0.1:sources@jar'
    compile 'com.raizlabs.android:ViewHolderInflater-Core:1.0.1'
    aarLinkSources 'com.raizlabs.android:ViewHolderInflater-Core:1.0.1:sources@jar'
    compile 'com.raizlabs.android:ViewHolderInflater:1.0.1'
    aarLinkSources 'com.raizlabs.android:ViewHolderInflater:1.0.1:sources@jar'
  }

```

# Changelog

## 1.0.1

  1. Generated code is now smarter about reusing found views. 
  2. Also easier to read by grouping view methods together

# How To Use


## Create a View Holder

Due to the relevance of ```RecyclerView``` is to the view holder pattern, our example
is a ```RecyclerView.ViewHolder```:

This is what it looks like before using ```ViewHolderInflater```:

```java

public class ExampleRecyclerVH extends RecyclerView.ViewHolder implements OnClickListener {

    TextView textView;

    CheckBox checkBox;

    Button someButton1;

    Button someButton2;

    public ExampleRecyclerVH(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.textView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        someButton1 = (Button) itemView.findViewById(R.id.someButton1);
        someButton1.setOnClickListener(this);
        someButton2 = (Button) itemView.findViewById(R.id.someButton2);
        someButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.someButton1:
                someButton1Action();
            break;
            case R.id.someButton2:
                someButton2Action();
            break;
        }

    }
}

```

In this example we ```findViewById()```, set ```OnClickListener``` on a few views, switch on those views,
and then call other methods based on the view chosen. Why do we need to do all of this code? Whenever
we have to change the type of the view, we have to go to its instantiation and change the casted type as well.

Using  ```ViewHolderInflater``` we can simplify the increasingly complex composite View Holders:


```java


@VHInflatable
@VHMethodInflatable
public class ExampleRecyclerVH extends RecyclerView.ViewHolder {

    @VHView(R.id.textView)
    TextView textView;

    @VHView(R.id.checkBox)
    CheckBox checkBox;

    @VHView(R.id.someButton1)
    Button someButton1;

    @VHView(R.id.someButton2)
    Button someButton2;

    public ExampleRecyclerVH(View itemView) {
        super(itemView);
        ViewHolderInflater.inflate(itemView, this);
    }

    @VHMethod
    void onClickSomeButton1() {
        // parameters are optional for all methods
        // in the right to left order they are in the method.
        // ex: onItemClick(AdapterView, View, int, long). you can remove parameters
        // one at a time starting from the right and it will still be valid.

        // Id is optional for all methods, it will default to the chunk after the method prefix.
        // in this case "onClickSomeButton1" -> "R.id.someButton1"
        // Same goes for @VHView except the exact name of the variable is used in the R.id lookup
    }

    @VHMethod(R.id.someButton2)
    void onClickSomeButton2(Button someButton2) {
        // method signature 1st param can be super-than or equal to the class of its id.

    }

}


```

## Concepts

```@VHView```: Generates a ```findViewById``` for the specified field. the id of the view is optional
as the compiler will use the variable name as a lookup within R.id.

```@VHInflatable```: tells the compiler that the class it refers to contains [1,...) ```@VHView``` definitions.
It must contain at least 1.

```@VHMethod```: Generates the code to "connect" the method to a callback on a view when calling
```ViewHolderInflater.connectViews()```. All methods MUST be prefixed with the following and have similar
signature to its corresponding ```View``` method (you can find all of them in ```VHDefaultMethodList```:
  1. onClick : ```OnClickListener```
  2. onItemClick: ```OnItemClickListener```
  3. onCheckedChanged: ```OnCheckedChangeListener```
  4. onTouch: ```OnTouchListener```
  5. onLongClick: ```OnLongClickListener```
  6. onCreate: Called before any connection occurs, use this method to instantiate a view.

```@VHMethodInflatable``` is required to suscribe to ```@VHMethod``` calls. This separation is
intentional as ```@VHMethod``` should never go in a ViewHolder.

```@VHMethodGroup```: enables multiple views to suscribe to the callback of one method. It follows
the same rules as ```@VHMethod``` for method prefixing, however the end chunk after the prefix is optional. Leaving
it out enables the class that the method is in to implement the intended interface anyways. For example ```OnClickListener```:

```java
@VHMethodInflatable
public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.main);
        ViewHolderInflater.connectViews(this, getWindow().getDecorView());
    }

    @Override
    @VHMethodGroup({R.id.textView, R.id.checkBox})
    public void onClick(View view) {

    }


}


```

This will connect the specified views to that method. So it enables flexibility if you prefer to
keep familiar methods and simply eliminate boilerplate definitions.
