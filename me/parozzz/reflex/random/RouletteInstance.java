/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.random;

/**
 * @author Paros
 */
public class RouletteInstance// extends BukkitRunnable
{
    /*
    private boolean notSet = false;

    private final Player p;
    private final List<ItemStack> items;
    private final UUID u;

    private ItemStack item;

    private final Listener listener;
    private Listener listener1_9;

    public RouletteInstance(final Player p, final UUID u, final ItemStack i, final List<ItemStack> items)
    {
        this.p = p;
        this.items = items;

        this.item = i;

        this.u = u;

        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(RouletteInstance.class);
        this.runTaskTimer(plugin, 3L, 3L);

        RouletteInstance instance = this;
        listener = new Listener()
        {
            @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
            private void onInteract(final PlayerInteractEvent e)
            {
                e.setCancelled(item.isSimilar(e.getItem()));
            }

            @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
            private void onInventoryClick(final InventoryClickEvent e)
            {
                switch(e.getAction())
                {
                    case COLLECT_TO_CURSOR:
                        e.setCancelled(item.isSimilar(e.getCursor()));
                        break;
                    default:
                        e.setCancelled(Optional.ofNullable(e.getCurrentItem()).filter(item::isSimilar).isPresent());
                        break;
                }

            }

            @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
            private void onDrop(final PlayerDropItemEvent e)
            {
                if(e.getPlayer().equals(p) && sameUUID(e.getItemDrop().getItemStack()))
                {
                    notSet = true;
                    e.setCancelled(true);
                }
            }

            @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
            private void onLogOut(final PlayerQuitEvent e)
            {
                if(e.getPlayer().equals(p))
                {
                    instance.end();
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(listener, plugin);

        listener1_9 = new Listener()
        {
            @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
            private void onSwap(final PlayerSwapHandItemsEvent e)
            {
                Optional.ofNullable(e.getOffHandItem()).filter(item::isSimilar).ifPresent(item -> e.setCancelled(true));
                Optional.ofNullable(e.getMainHandItem()).filter(item::isSimilar).ifPresent(item -> e.setCancelled(true));
            }
        };
        Bukkit.getPluginManager().registerEvents(listener1_9, plugin);
    }

    public Player getPlayer()
    {
        return p;
    }

    public ItemStack getRandomItem()
    {
        return items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }

    private int count = 30;

    @Override
    public void run()
    {
        if(notSet)
        {
            Stream.of(p.getInventory().getContents()).filter(this::sameUUID).findFirst().ifPresent(invItem ->
            {
                item = invItem;
                notSet = false;
            });
        }

        if(count-- == 0)
        {
            end();
            new FireworkBuilder().addColor(Color.BLUE).addFadeColor(Color.AQUA).setType(FireworkEffect.Type.BALL_LARGE).spawn(p.getLocation(), 1);
            return;
        }

        changeItem();
    }

    private boolean sameUUID(final ItemStack item)
    {
        NBTCompound tag = new NMSStackCompound(item);
        return tag.hasKey(RouletteItem.UID) && u.equals(UUID.fromString(tag.getString(RouletteItem.UID)));
    }

    private void end()
    {
        changeItem();

        NMSStackCompound stack = new NMSStackCompound(item);
        stack.removeKey(RouletteItem.UID);
        item.setItemMeta(stack.getItemStack().getItemMeta());

        this.cancel();
        HandlerList.unregisterAll(listener);
        Optional.ofNullable(listener1_9).ifPresent(l -> HandlerList.unregisterAll(l));
    }

    private void changeItem()
    {
        ItemStack random = this.getRandomItem();

        item.setType(random.getType());
        item.setAmount(random.getAmount());
        item.setDurability(random.getDurability());
        item.setData(random.getData());
        item.setItemMeta(random.getItemMeta());
    }*/
}
