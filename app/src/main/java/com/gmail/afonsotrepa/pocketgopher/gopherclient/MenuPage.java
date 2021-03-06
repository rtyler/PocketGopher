package com.gmail.afonsotrepa.pocketgopher.gopherclient;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.gmail.afonsotrepa.pocketgopher.History;
import com.gmail.afonsotrepa.pocketgopher.R;
import com.gmail.afonsotrepa.pocketgopher.gopherclient.Activity.MenuActivity;


/**
 * Directory/Menu ('1')
 */

public class MenuPage extends Page
{
    private static final Integer IMAGE_TAG = R.drawable.ic_folder_open_white;

    public MenuPage(String selector, String server, Integer port, String line)
    {
        super(server, port, '1', selector, line);
    }

    public MenuPage(String selector, String server, Integer port)
    {
        this(selector, server, port, null);
    }


    public void render(final TextView textView, final Context context, String line)
    {
        //handler to the main thread
        final Handler handler = new Handler(Looper.getMainLooper());
        final SpannableString text = new SpannableString("  " + line + " \n");

        final Page page = this;

        //create the span (and the function to be run when it's clicked)
        final ClickableSpan cs1 = new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                page.open(context);
            }
        };
        final ClickableSpan cs2 = new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                page.open(context);
            }
        };


        //apply the span to text and append text to textView
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                //make it clickable
                text.setSpan(cs1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setSpan(cs2, 2, text.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //set the image tag behind (left of) the text
                text.setSpan(Page.formatIcon(context, textView, IMAGE_TAG) , 0, 1, 0);
                //add it to the end of textView
                textView.append(text);
            }
        });
    }


    public void open(Context context)
    {
        History.add(context, this.url);

        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtra("page", this);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //needed for API 19
        context.startActivity(intent);
    }
}
