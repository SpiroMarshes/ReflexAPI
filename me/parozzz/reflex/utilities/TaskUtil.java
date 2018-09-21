/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Paros
 */
public class TaskUtil 
{
    /**
     * 
     * @param <T> - The type of the collection
     * @param s  - The collection to split
     * @param chunkSize - How much chunks should the collection be splitted
     * @return - The splitted collection as a List of lists
     */
    public static <T> List<List<T>> splitCollection(final Collection<T> s, final int chunkSize) 
    {
        return splitStream(s.stream(), chunkSize).collect(Collectors.toList());
    }
    
    /**
     * 
     * @param <T> - The type of the collection
     * @param s - The stream to split
     * @param chunkSize - How much chunks should the stream be splitted
     * @return - The splitted stream as a Stream of lists
     */
    public static <T> Stream<List<T>> splitStream(final Stream<T> s, int chunkSize) 
    {
        if(chunkSize <= 1) 
        {
            return s.map(Collections::singletonList);
        }
        
        Spliterator<T> src=s.spliterator();
        
        long size=src.estimateSize();
        if(size != Long.MAX_VALUE) 
        {
            size=(size+chunkSize-1)/chunkSize;
        }
        
        int ch=src.characteristics();
        ch&=Spliterator.SIZED|Spliterator.ORDERED|Spliterator.DISTINCT|Spliterator.IMMUTABLE;
        ch|=Spliterator.NONNULL;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<List<T>>(size, ch)
        {
            private List<T> current;
            @Override
            public boolean tryAdvance(Consumer<? super List<T>> action) 
            {
                if(current == null) 
                {
                    current = new ArrayList<>(chunkSize);
                }
                
                while(current.size()<chunkSize && src.tryAdvance(current::add));
                
                if(!current.isEmpty()) 
                {
                    action.accept(current);
                    current = null;
                    return true;
                }
                return false;
            }
        }, s.isParallel());
    }
}
