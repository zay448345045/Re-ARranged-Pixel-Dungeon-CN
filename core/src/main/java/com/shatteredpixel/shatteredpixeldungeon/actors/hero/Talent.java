/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.level;
import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HorseRiding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PhysicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ScrollEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WandEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.WatchTower;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.TalentSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.BulletItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.ShardOfOblivion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blooming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public enum Talent {

	//Warrior T1
	HEARTY_MEAL					(0,  0),
	VETERANS_INTUITION			(1,  0),
	PROVOKED_ANGER				(2,  0),
	IRON_WILL					(3,  0),
	MAX_HEALTH					(4,  0),
	//Warrior T2
	IRON_STOMACH				(5,  0),
	LIQUID_WILLPOWER			(6,  0),
	RUNIC_TRANSFERENCE			(7,  0),
	LETHAL_MOMENTUM				(8,  0),
	IMPROVISED_PROJECTILES		(9,  0),
	PARRY						(10, 0),
	//Warrior T3
	HOLD_FAST					(11, 0, 3),
	STRONGMAN					(12, 0, 3),
	//Berserker T3
	ENDLESS_RAGE				(13, 0, 3),
	DEATHLESS_FURY				(14, 0, 3),
	ENRAGED_CATALYST			(15, 0, 3),
	LETHAL_RAGE					(16, 0, 3),
	MAX_RAGE					(17, 0, 3),
	ENDURANCE					(18, 0, 3),
	//Gladiator T3
	CLEAVE						(19, 0, 3),
	LETHAL_DEFENSE				(20, 0, 3),
	ENHANCED_COMBO				(21, 0, 3),
	LIGHT_WEAPON				(22, 0, 3),
	OFFENSIVE_DEFENSE			(23, 0, 3),
	SKILL_REPEAT				(24, 0, 3),
	//Veteran T3
	POWERFUL_TACKLE				(25, 0, 3),
	MYSTICAL_TACKLE				(26, 0, 3),
	DELAYED_GRENADE				(27, 0, 3),
	INCAPACITATION				(28, 0, 3),
	SUPER_ARMOR					(29, 0, 3),
	IMPROVED_TACKLE				(30, 0, 3),
	//Heroic Leap T4
	BODY_SLAM					(31, 0, 4),
	IMPACT_WAVE					(32, 0, 4),
	DOUBLE_JUMP					(33, 0, 4),
	//Shockwave T4
	EXPANDING_WAVE				(34, 0, 4),
	STRIKING_WAVE				(35, 0, 4),
	SHOCK_FORCE					(36, 0, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION		(37, 0, 4),
	SHRUG_IT_OFF				(38, 0, 4),
	EVEN_THE_ODDS				(39, 0, 4),

	//Mage T1
	EMPOWERING_MEAL				(0,  1),
	SCHOLARS_INTUITION			(1,  1),
	LINGERING_MAGIC				(2,  1),
	BACKUP_BARRIER				(3,  1),
	CHARGE_PRESERVE				(4,  1),
	//Mage T2
	ENERGIZING_MEAL				(5,  1),
	INSCRIBED_POWER				(6,  1),
	WAND_PRESERVATION			(7,  1),
	ARCANE_VISION				(8,  1),
	SHIELD_BATTERY				(9,  1),
	FASTER_CHARGER				(10, 1),
	//Mage T3
	DESPERATE_POWER				(11, 1, 3),
	ALLY_WARP					(12, 1, 3),
	//Battlemage T3
	EMPOWERED_STRIKE			(13, 1, 3),
	MYSTICAL_CHARGE				(14, 1, 3),
	EXCESS_CHARGE				(15, 1, 3),
	BATTLE_MAGIC				(16, 1, 3),
	MAGIC_RUSH					(17, 1, 3),
	MAGICAL_CIRCLE				(18, 1, 3),
	//Warlock T3
	SOUL_EATER					(19, 1, 3),
	SOUL_SIPHON					(20, 1, 3),
	NECROMANCERS_MINIONS		(21, 1, 3),
	MADNESS						(22, 1, 3),
	ENHANCED_MARK				(23, 1, 3),
	MARK_OF_WEAKNESS			(24, 1, 3),
	//Wizard T3
	SPELL_ENHANCE				(25, 1, 3),
	BRIG_BOOST					(26, 1, 3),
	ENERGY_REMAINS				(27, 1, 3),
	MAGIC_EMPOWER				(28, 1, 3),
	SECOND_EFFECT				(29, 1, 3),
	LIFE_ENERGY					(30, 1, 3),
	//Elemental Blast T4
	BLAST_RADIUS				(31, 1, 4),
	ELEMENTAL_POWER				(32, 1, 4),
	REACTIVE_BARRIER			(33, 1, 4),
	//Wild Magic T4
	WILD_POWER					(34, 1, 4),
	FIRE_EVERYTHING				(35, 1, 4),
	CONSERVED_MAGIC				(36, 1, 4),
	//Warp Beacon T4
	TELEFRAG					(37, 1, 4),
	REMOTE_BEACON				(38, 1, 4),
	LONGRANGE_WARP				(39, 1, 4),

	//Rogue T1
	CACHED_RATIONS				(0,  2),
	THIEFS_INTUITION			(1,  2),
	SUCKER_PUNCH				(2,  2),
	PROTECTIVE_SHADOWS			(3,  2),
	EMERGENCY_ESCAPE			(4,  2),
	//Rogue T2
	MYSTICAL_MEAL				(5,  2),
	INSCRIBED_STEALTH			(6,  2),
	WIDE_SEARCH					(7,  2),
	SILENT_STEPS				(8,  2),
	ROGUES_FORESIGHT			(9,  2),
	MOVESPEED_ENHANCE			(10, 2),
	//Rogue T3
	ENHANCED_RINGS				(11, 2, 3),
	LIGHT_CLOAK					(12, 2, 3),
	//Assassin T3
	ENHANCED_LETHALITY			(13, 2, 3),
	ASSASSINS_REACH				(14, 2, 3),
	BOUNTY_HUNTER				(15, 2, 3),
	ENERGY_DRAW					(16, 2, 3),
	PERFECT_ASSASSIN			(17, 2, 3),
	CAUTIOUS_PREP				(18, 2, 3),
	//Freerunner T3
	EVASIVE_ARMOR				(19, 2, 3),
	PROJECTILE_MOMENTUM			(20, 2, 3),
	SPEEDY_STEALTH				(21, 2, 3),
	QUICK_PREP					(22, 2, 3),
	OVERCOMING					(23, 2, 3),
	MOMENTARY_FOCUSING			(24, 2, 3),
	//Chaser T3
	POISONOUS_BLADE				(25, 2, 3),
	LETHAL_SURPRISE				(26, 2, 3),
	CHAIN_CLOCK					(27, 2, 3),
	SOUL_COLLECT				(28, 2, 3),
	TRAIL_TRACKING				(29, 2, 3),
	MASTER_OF_CLOAKING			(30, 2, 3),
	//Smoke Bomb T4
	HASTY_RETREAT				(31, 2, 4),
	BODY_REPLACEMENT			(32, 2, 4),
	SHADOW_STEP					(33, 2, 4),
	//Death Mark T4
	FEAR_THE_REAPER				(34, 2, 4),
	DEATHLY_DURABILITY			(35, 2, 4),
	DOUBLE_MARK					(36, 2, 4),
	//Shadow Clone T4
	SHADOW_BLADE				(37, 2, 4),
	CLONED_ARMOR				(38, 2, 4),
	PERFECT_COPY				(39, 2, 4),

	//Huntress T1
	NATURES_BOUNTY				(0,  3),
	SURVIVALISTS_INTUITION		(1,  3),
	FOLLOWUP_STRIKE				(2,  3),
	NATURES_AID					(3,  3),
	WATER_FRIENDLY				(4,  3),
	//Huntress T2
	INVIGORATING_MEAL			(5,  3),
	LIQUID_NATURE				(6,  3),
	REJUVENATING_STEPS			(7,  3),
	HEIGHTENED_SENSES			(8,  3),
	DURABLE_PROJECTILES			(9,  3),
	ADDED_MEAL					(10, 3),
	//Huntress T3
	POINT_BLANK					(11, 3, 3),
	SEER_SHOT					(12, 3, 3),
	//Sniper T3
	FARSIGHT					(13, 3, 3),
	SHARED_ENCHANTMENT			(14, 3, 3),
	SHARED_UPGRADES				(15, 3, 3),
	KICK						(16, 3, 3),
	SHOOTING_EYES				(17, 3, 3),
	TARGET_SPOTTING				(18, 3, 3),
	//Warden T3
	DURABLE_TIPS				(19, 3, 3),
	BARKSKIN					(20, 3, 3),
	SHIELDING_DEW				(21, 3, 3),
	LIVING_GRASS				(22, 3, 3),
	ATTRACTION					(23, 3, 3),
	HEALING_DEW					(24, 3, 3),
	//Fighter T3
	SWIFT_MOVEMENT				(25, 3, 3),
	LESS_RESIST					(26, 3, 3),
	RING_KNUCKLE				(27, 3, 3),
	MYSTICAL_PUNCH				(28, 3, 3),
	QUICK_STEP					(29, 3, 3),
	COUNTER_ATTACK				(30, 3, 3),
	//Spectral Blades T4
	FAN_OF_BLADES				(31, 3, 4),
	PROJECTING_BLADES			(32, 3, 4),
	SPIRIT_BLADES				(33, 3, 4),
	//Natures Power T4
	GROWING_POWER				(34, 3, 4),
	NATURES_WRATH				(35, 3, 4),
	WILD_MOMENTUM				(36, 3, 4),
	//Spirit Hawk T4
	EAGLE_EYE					(37, 3, 4),
	GO_FOR_THE_EYES				(38, 3, 4),
	SWIFT_SPIRIT				(39, 3, 4),

	//Duelist T1
	STRENGTHENING_MEAL			(0,  4),
	ADVENTURERS_INTUITION		(1,  4),
	PATIENT_STRIKE				(2,  4),
	AGGRESSIVE_BARRIER			(3,  4),
	SKILLED_HAND				(4,  4),
	//Duelist T2
	FOCUSED_MEAL				(5,  4),
	LIQUID_AGILITY				(6,  4),
	WEAPON_RECHARGING			(7,  4),
	LETHAL_HASTE				(8,  4),
	SWIFT_EQUIP					(9,  4),
	ACCUMULATION				(10, 4),
	//Duelist T3
	PRECISE_ASSAULT				(11, 4, 3),
	DEADLY_FOLLOWUP				(12, 4, 3),
	//Champion T3
	VARIED_CHARGE				(13, 4, 3),
	TWIN_UPGRADES				(14, 4, 3),
	COMBINED_LETHALITY			(15, 4, 3),
	FASTER_CHARGE				(16, 4, 3),
	QUICK_FOLLOWUP				(17, 4, 3),
	TWIN_SWORD					(18, 4, 3),
	//Monk T3
	UNENCUMBERED_SPIRIT			(19, 4, 3),
	MONASTIC_VIGOR				(20, 4, 3),
	COMBINED_ENERGY				(21, 4, 3),
	RESTORED_ENERGY				(22, 4, 3),
	ENERGY_BARRIER				(23, 4, 3),
	HARMONY						(24, 4, 3),
	//Fencer T3
	CLAM_STEPS					(25, 4, 3),
	CRITICAL_MOMENTUM			(26, 4, 3),
	KINETIC_MOVEMENT			(27, 4, 3),
	AGGRESSIVE_MOVEMENT			(28, 4, 3),
	UNENCUMBERED_MOVEMENT		(29, 4, 3),
	SOULIZE						(30, 4, 3),
	//Challenge T4
	CLOSE_THE_GAP				(31, 4, 4),
	INVIGORATING_VICTORY		(32, 4, 4),
	ELIMINATION_MATCH			(33, 4, 4),
	//Elemental Strike T4
	ELEMENTAL_REACH				(34, 4, 4),
	STRIKING_FORCE				(35, 4, 4),
	DIRECTED_POWER				(36, 4, 4),
	//Feint T4
	FEIGNED_RETREAT				(37, 4, 4),
	EXPOSE_WEAKNESS				(38, 4, 4),
	COUNTER_ABILITY				(39, 4, 4),

	//Gunner T1
	RELOADING_MEAL				(0,  6),	//식사 시 장착한 총기 재장전/1발 더 재장전
	GUNNERS_INTUITION			(1,  6),	//총기를 장착 시 감정/습득 시 저주 여부 감정
	SPEEDY_MOVE					(2,  6),	//적을 처음 공격하면 신속 2/3턴
	SAFE_RELOAD					(3,  6),	//재장전 시 3/5의 방어막을 얻음
	CLOSE_COMBAT				(4,  6),	//총기 근접 공격력이 0-4/0-6 증가
	//Gunner T2
	INFINITE_BULLET_MEAL		(5,  6),	//식사에 1턴만 소모하고, 식사 시 2/3턴의 무한 탄환을 얻음
	INSCRIBED_BULLET			(6,  6),	//주문서 사용 시 5/10개의 탄환을 얻음
	MIND_VISION					(7,  6),	//이동 시 1%/2% 확률로 1턴의 심안을 얻음
	CAMOUFLAGE					(8,  6),	//길게 자란 풀을 밟으면 2/3턴의 투명화를 얻음
	LARGER_MAGAZINE				(9,  6),	//총기의 최대 탄약 수 1/2 증가
	BULLET_COLLECT				(10, 6),	//적을 처치하면 1/2개의 탄환을 드랍함
	//Gunner T3
	STREET_BATTLE				(11, 6, 3),	//탄환이 플레이어 주변 반경 2/3/4타일의 벽을 관통함
	FAST_RELOAD					(12, 6, 3),	//재장전에 필요한 턴이 1/2/3턴 감소
	//Outlaw T3
	ROLLING						(13, 6, 3),	//총을 쏜 이후 1/2/3턴 이내에 움직일 경우 탄환을 1발 장전함
	PERFECT_FOCUSING			(14, 6, 3),	//죽음의 룰렛 지속 시간이 2/4/6턴 증가함
	HONORABLE_SHOT				(15, 6, 3),	//죽음의 룰렛 스택 3 이상에서, 적의 공격을 회피한 직후 탄환을 발사하면 13/27/40% 미만의 체력을 가진 적을 즉사시킴
	BULLET_TIME					(16, 6, 3),	//죽음의 룰렛 스택 6으로 적을 즉사시키면 시야 내의 모든 적을 4/8/12턴 동안 둔화시킴
	INEVITABLE_DEATH			(17, 6, 3),	//죽음의 룰렛 스택 6이 되면 다음 탄환 공격이 2배/3배/4배의 정확성을 얻음
	HEADSHOT					(18, 6, 3),	//탄환이 1%/2%/3% 확률로 적을 즉사시킴
	//Gunslinger T3
	QUICK_RELOAD				(19, 6, 3),	//이동 시 3%/6%/9% 확률로 탄환을 1발 장전함
	MOVING_SHOT					(20, 6, 3),	//이동 직후 투척 무기와 탄환의 명중률 감소량이 17%/33%/50% 감소
	ELEMENTAL_BULLET			(21, 6, 3),	//탄환을 전부 소모한 상태에서 재장전 시 각 17% 확률로 빙결탄/화염탄/전격탄을 장전함
	IMPROVISATION				(22, 6, 3),	//탄환을 전부 소모하면 8/16/24의 방어막을 얻음
	SOUL_BULLET					(23, 6, 3),	//적을 처치하면 1/2/3턴의 무한 탄환을 얻음
	LIGHT_MOVEMENT				(24, 6, 3),	//갑옷의 힘 요구 수치를 초과한 힘 3/2/1당 이동 속도가 5% 증가
	//Specialist T3
	STEALTH_MASTER				(25, 6, 3),	//총기 어그로 제거/적이 근처에 있어도 은폐 가능/은폐 중 허기 소모 X
	SKILLFUL_RUNNER				(26, 6, 3),	//은폐가 해제될 때 2/4/6턴의 신속을 얻음. 재사용 대기 시간 30턴
	STEALTH						(27, 6, 3),	//적에게 들킬 확률 1/2/3단계 감소
	INTO_THE_SHADOW				(28, 6, 3),	//은폐 시도 시 은폐 대신 3/6/9턴의 투명화를 얻음. 재사용 대기 시간 15턴
	RANGED_SNIPING				(29, 6, 3),	//적과의 거리 1타일 당 투척 무기의 피해량이 2.5%/5%/7.5%씩 증가
	TELESCOPE					(30, 6, 3),	//시야 범위 25%/50%/75% 증가
	//Riot T4
	HASTE_MOVE					(31, 6, 4),
	SHOT_CONCENTRATION			(32, 6, 4),
	ROUND_PRESERVE				(33, 6, 4),
	//ReinforcedArmor T4
	BAYONET						(34, 6, 4),
	TACTICAL_SIGHT				(35, 6, 4),
	PLATE_ADD					(36, 6, 4),
	//FirstAidKit T4
	ADDITIONAL_MEDS				(37, 6, 4),
	THERAPEUTIC_BANDAGE			(38, 6, 4),
	FASTER_HEALING				(39, 6, 4),

	//Samurai T1
	BASIC_PRACTICE				(0, 7),	//치명 확률 2/4% 증가
	MASTERS_INTUITION			(1, 7),	//총기를 제외한 근접 무기 장착 시 즉시 감정/총기를 제외한 근접 무기 획득 시 저주 여부 감정
	DRAWING_ENHANCE				(2, 7),	//발도술 공격이 2/3의 추가 피해를 입힘
	PARRING						(3, 7),	//방어력 0-2/0-3 증가
	ADRENALINE_SURGE			(4, 7),	//적 처치 시 3/5턴의 아드레날린 획득
	//Samurai T2
	CRITICAL_MEAL				(5, 7),	//식사에 1턴만 소모, 식사 시 다음 1/2회의 물리 공격에 반드시 치명타 발생
	INSCRIBED_LETHALITY			(6, 7),	//주문서 사용 시 다음 1/2회의 물리 공격에 반드시 치명타 발생
	UNEXPECTED_SLASH			(7, 7),	//적을 처음 공격하면 치명 확률 +10/20% 증가
	DRAGONS_EYE					(8, 7),	//납도 중 주변 반경 3/4타일 이내의 적 위치를 파악할 수 있게 됨
	WEAPON_MASTERY				(9, 7),	//무기의 힘 요구 수치를 넘은 힘 1당 치명 확률 +1/+2%
	CRITICAL_THROW				(10, 7),	//투척 무기와 탄환의 치명 확률이 +25/50% 증가
	//Samurai T3
	QUICK_SHEATHING				(11, 7, 3),
	LETHAL_POWER				(12, 7, 3),
	//Slasher T3
	MIND_FOCUSING				(13, 7, 3),
	STORED_POWER				(14, 7, 3),
	ARCANE_POWER				(15, 7, 3),
	ENERGY_COLLECT				(16, 7, 3),
	DIVIDED_POWER				(17, 7, 3),
	WIND_BLAST					(18, 7, 3),
	//Master T3
	ENHANCED_CRIT				(19, 7, 3),
	POWERFUL_SLASH				(20, 7, 3),
	STATIC_PREPARATION			(21, 7, 3),
	ACCELERATION				(22, 7, 3),
	INNER_EYE					(23, 7, 3),
	DYNAMIC_PREPARATION			(24, 7, 3),
	//Slayer T3
	FASTER_THAN_LIGHT			(25, 7, 3),
	AFTERIMAGE					(26, 7, 3),
	QUICK_RECOVER				(27, 7, 3),
	HASTE_EVASION				(28, 7, 3),
	ACCELERATED_LETHALITY		(29, 7, 3),
	STABLE_BARRIER				(30, 7, 3),
	//Awake T4
	AWAKE_LIMIT					(31, 7, 4),
	AWAKE_DURATION				(32, 7, 4),
	INSURANCE					(33, 7, 4),
	//ShadowBlade T4
	DOUBLE_BLADE_PRACTICE		(34, 7, 4),
	CRITICAL_SHADOW				(35, 7, 4),
	HERBAL_SHADOW				(36, 7, 4),
	//Kunai T4
	KUNAI_OF_DOOM				(37, 7, 4),
	MYSTICAL_KUNAI				(38, 7, 4),
	CORROSIVE_KUNAI				(39, 7, 4),

	//Adventurer T1
	NATURE_FRIENDLY				(0, 8),
	SAFE_POTION					(1, 8),
	ROOT						(2, 8),
	PLANT_BARRIER				(3, 8),
	ROPE_MAKING					(4, 8),
	//Adventurer T2
	NATURES_MEAL				(5, 8),
	PHARMACEUTICS				(6, 8),
	HERB_EXTRACTION				(7, 8),
	FIREWATCH					(8, 8),
	ROPE_REBOUND				(9, 8),
	WEAKENING_POISON			(10, 8),
	//Adventurer T3
	LONG_MACHETE				(11, 8, 3),
	BLOOMING_WEAPON				(12, 8, 3),
	//Engineer T3
	BARRICADE					(13, 8, 3),
	WIRE						(14, 8, 3),
	WATCHTOWER					(15, 8, 3),
	CANNON						(16, 8, 3),
	MACHINEGUN					(17, 8, 3),
	MORTAR						(18, 8, 3),
	//Explorer T3
	JUNGLE_EXPLORE				(19, 8, 3),
	DURABLE_ROPE				(20, 8, 3),
	LASSO						(21, 8, 3),
	ROPE_COLLECTOR				(22, 8, 3),
	ROPE_MASTER					(23, 8, 3),
	VINE_BIND					(24, 8, 3),
	//Researcher T3
	BIOLOGY_PROJECT				(25, 8, 3),
	RAPID_GROWTH				(26, 8, 3),
	BIO_ENERGY					(27, 8, 3),
	WATER_ABSORB				(28, 8, 3),
	POWERFUL_ACID				(29, 8, 3),
	STICKY_OOZE					(30, 8, 3),
	//Sprout T4
	JUNGLE						(31, 8, 4),
	FOREST						(32, 8, 4),
	REGROWTH					(33, 8, 4),
	//TreasureMap T4
	LONG_LUCK					(34, 8, 4),
	FORESIGHT					(35, 8, 4),
	GOLD_HUNTER					(36, 8, 4),
	//Root T4
	POISONOUS_ROOT				(37, 8, 4),
	ROOT_SPREAD					(38, 8, 4),
	ROOT_ARMOR					(39, 8, 4),



	//Knight T1
	TOUGH_MEAL					(0, 9),
	KNIGHTS_INTUITION			(1, 9),
	KINETIC_BATTLE				(2, 9),
	HARD_SHIELD					(3, 9),
	WAR_CRY						(4, 9),
	//Knight T2
	IMPREGNABLE_MEAL			(5, 9),
	SMITHING_SPELL				(6, 9),
	ARMOR_ADAPTION				(7, 9),
	CHIVALRY					(8, 9),
	PROTECTION					(9, 9),
	FLAG_OF_CONQUEST			(10, 9),
	//Knight T3
	CRAFTMANS_SKILLS			(11, 9, 3),
	TACKLE						(12, 9, 3),
	//DeathKnight T3
	ARMY_OF_DEATH				(13, 9, 3),
	DEATHS_CHILL				(14, 9, 3),
	OVERCOME					(15, 9, 3),
	RESENTMENT					(16, 9, 3),
	UNDEAD						(17, 9, 3),
	DEATHS_FEAR					(18, 9, 3),
	//Horseman T3
	SHOCKWAVE					(19, 9, 3),
	CALL_OF_MASTER				(20, 9, 3),
	DASH_ENHANCE				(21, 9, 3),
	BUFFER						(22, 9, 3),
	PARKOUR						(23, 9, 3),
	PILOTING					(24, 9, 3),
	//Crusader T3
	HOLY_SHIELD					(25, 9, 3),
	PRAY_FOR_DEAD				(26, 9, 3),
	JUDGEMENT					(27, 9, 3),
	CLEANSING_PRAY				(28, 9, 3),
	PUNISHMENT					(29, 9, 3),
	ANTI_DEMON					(30, 9, 3),
	//HolyShield T4
	BUFFER_BARRIER				(31, 9, 4),
	HOLY_LIGHT					(32, 9, 4),
	BLESS						(33, 9, 4),
	//StimPack T4
	BURDEN_RELIEF				(34, 9, 4),
	LASTING_PACK				(35, 9, 4),
	TIME_STOP					(36, 9, 4),
	//UnstableAnkh T4
	BLESSED_ANKH				(37, 9, 4),
	ANKH_ENHANCE				(38, 9, 4),
	COMPLETE_ANKH				(39, 9, 4),

	//Medic T1
	SCAR_ATTACK					(0, 9),
	DOCTORS_INTUITION			(1, 9),
	PRECISION_HANDSKILL			(2, 9),
	FIRST_AID_TREAT				(3, 9),
	BREAKTHROUGH				(4, 9),
	//Medic T2
	HEALING_MEAL				(5, 10),
	RECYCLING					(6, 10),
	HIGH_POWER					(7, 10),
	RADIATION					(8, 10),
	STRONG_HEALPOWER			(9, 10),
	DIET						(10, 10),
	//Medic T3
	STRONG_NEXUS				(11, 10, 3),
	TARGET_SET					(12, 10, 3),
	//Savior T3
	RECRUIT						(13, 10, 3),
	DELAYED_HEALING				(14, 10, 3),
	APPEASE						(15, 10, 3),
	ADRENALINE					(16, 10, 3),
	STIMPACK					(17, 10, 3),
	MEDICAL_RAY					(18, 10, 3),
	//Therapist T3
	OINTMENT					(19, 10, 3),
	COMPRESS_BANDAGE			(20, 10, 3),
	ANTIBIOTICS					(21, 10, 3),
	QUICK_PREPARE				(22, 10, 3),
	SPLINT						(23, 10, 3),
	DEFIBRILLATOR				(24, 10, 3),
	//MedicalOfficer T3
	MOVE_ORDER					(25, 10, 3),
	REINFORCE_ORDER				(26, 10, 3),
	ENGINEER_ORDER				(27, 10, 3),
	STANCE_ORDER				(28, 10, 3),
	ATTACK_ORDER				(29, 10, 3),
	CAS_ORDER					(30, 10, 3),
	//HealGenerator T4
	AREA_AMP					(31, 10, 4),
	SHIELD_GEN					(32, 10, 4),
	DURABLE_GEN					(33, 10, 4),
	//AngelWing T4
	LIGHT_LIKE_FEATHER			(34, 10, 4),
	ANGELS_BLESS				(35, 10, 4),
	HEALING_WING				(36, 10, 4),
	//GammaRayEmmit T4
	TRANSMOG_BIAS				(37, 10, 4),
	IMPRINTING_EFFECT			(38, 10, 4),
	SHEEP_TRANSMOG				(39, 10, 4),

	//universal T4
	HEROIC_ENERGY				(43, 0, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE					(40, 12, 4),
	RATLOMACY					(41, 12, 4),
	RATFORCEMENTS				(42, 12, 4),
	//universal T3
	ATK_SPEED_ENHANCE			(0, 12, 4),
	ACC_ENHANCE					(1, 12, 4),
	EVA_ENHANCE					(2, 12, 4),
	BETTER_CHOICE				(3, 12, 3);


	public static final int TALENT_NUMBER = 44;

	//warrior talent's buff
	//2-4
	public static class LethalMomentumTracker extends FlavourBuff{};
	//2-5
	public static class ImprovisedProjectileCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.15f, 0.2f, 0.5f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
	};
	//Berserker
	public static class MaxRageCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xFF3333); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	//Shockwave 4-3
	public static class StrikingWaveTracker extends FlavourBuff{};



	//mage talent's buff
	//2-3
	public static class WandPreservationCounter extends CounterBuff{{revivePersists = true;}};
	//BattleMage 3-3
	public static class EmpoweredStrikeTracker extends FlavourBuff{
		//blast wave on-hit doesn't resolve instantly, so we delay detaching for it
		public boolean delayedDetach = false;
	};



	//rouge talent's buff
	//1-4
	public static class ProtectiveShadowsTracker extends Buff {
		float barrierInc = 0.5f;

		@Override
		public boolean act() {
			//barrier every 2/1 turns, to a max of 3/5
			if (((Hero)target).hasTalent(Talent.PROTECTIVE_SHADOWS) && target.invisible > 0){
				Barrier barrier = Buff.affect(target, Barrier.class);
				if (barrier.shielding() < 1 + 2*((Hero)target).pointsInTalent(Talent.PROTECTIVE_SHADOWS)) {
					barrierInc += 0.5f * ((Hero) target).pointsInTalent(Talent.PROTECTIVE_SHADOWS);
				}
				if (barrierInc >= 1){
					barrierInc = 0;
					barrier.incShield(1);
				} else {
					barrier.incShield(0); //resets barrier decay
				}
			} else {
				detach();
			}
			spend( TICK );
			return true;
		}

		private static final String BARRIER_INC = "barrier_inc";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( BARRIER_INC, barrierInc);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			barrierInc = bundle.getFloat( BARRIER_INC );
		}
	}
	//Assassin 3-5
	public static class BountyHunterTracker extends FlavourBuff{};
	//Chaser
	public static class ChaseCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.4f, 0.4f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (15)); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
	};
	//Chaser 3-4
	public static class LethalCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.8f, 0.1f, 0.1f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
	};
	//Chaser 3-5
	public static class ChainCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.2f, 0.5f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
	};


	//huntress talent's buff
	//2-3
	public static class RejuvenatingStepsCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0f, 0.35f, 0.15f); }
		public float iconFadePercent() { return GameMath.gate(0, visualcooldown() / (15 - 5*Dungeon.hero.pointsInTalent(REJUVENATING_STEPS)), 1); }
	};
	public static class RejuvenatingStepsFurrow extends CounterBuff{{revivePersists = true;}};
	//3-2
	public static class SeerShotCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.7f, 0.4f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 20); }
	};
	//Spectral Blades 4-3
	public static class SpiritBladesTracker extends FlavourBuff{};
	//Sniper
	public static class KickCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xF27318); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class ShootingEyesTracker extends Buff{};
	//Fighter
	public static class QuickStep extends FlavourBuff {

		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		public static final float DURATION	= 1f;

		@Override
		public int icon() {
			return BuffIndicator.HASTE;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0x2364BC);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns());
		}
	}
	//Fighter 3-8
	public static class CounterAttackTracker extends Buff{};


	//duelist talent's buff
	//1-3
	public static class PatientStrikeTracker extends Buff {
		public int pos;
		{ type = Buff.buffType.POSITIVE; }
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.5f, 0f, 1f); }
		@Override
		public boolean act() {
			if (pos != target.pos) {
				detach();
			} else {
				spend(TICK);
			}
			return true;
		}
		private static final String POS = "pos";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(POS, pos);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			pos = bundle.getInt(POS);
		}
	};
	//1-4
	public static class AggressiveBarrierCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.35f, 0f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
	};
	//2-2
	public static class LiquidAgilEVATracker extends FlavourBuff{};
	public static class LiquidAgilACCTracker extends FlavourBuff{
		public int uses;

		{ type = buffType.POSITIVE; }
		public int icon() { return BuffIndicator.INVERT_MARK; }
		public void tintIcon(Image icon) { icon.hardlight(0.5f, 0f, 1f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }

		private static final String USES = "uses";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(USES, uses);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			uses = bundle.getInt(USES);
		}
	};
	public static class LethalHasteCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.35f, 0f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 100); }
	};
	//2-5
	public static class SwiftEquipCooldown extends FlavourBuff{
		public boolean secondUse;
		public boolean hasSecondUse(){
			return secondUse;
		}

		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) {
			if (hasSecondUse()) icon.hardlight(0.85f, 0f, 1.0f);
			else                icon.hardlight(0.35f, 0f, 0.7f);
		}
		public float iconFadePercent() { return GameMath.gate(0, visualcooldown() / 20f, 1); }

		private static final String SECOND_USE = "second_use";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SECOND_USE, secondUse);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			secondUse = bundle.getBoolean(SECOND_USE);
		}
	};
	//2-6
	public static class SkilledHandTracker extends Buff{};
	//3-1
	public static class PreciseAssaultTracker extends FlavourBuff{
		{ type = buffType.POSITIVE; }
		public int icon() { return BuffIndicator.INVERT_MARK; }
		public void tintIcon(Image icon) { icon.hardlight(1f, 1f, 0.0f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }
	};
	//3-2
	public static class DeadlyFollowupTracker extends FlavourBuff{
		public int object;
		{ type = Buff.buffType.POSITIVE; }
		public int icon() { return BuffIndicator.INVERT_MARK; }
		public void tintIcon(Image icon) { icon.hardlight(0.5f, 0f, 1f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }
		private static final String OBJECT    = "object";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(OBJECT, object);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			object = bundle.getInt(OBJECT);
		}
	}
	public static class VariedChargeTracker extends Buff{
		public Class weapon;

		private static final String WEAPON    = "weapon";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(WEAPON, weapon);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			weapon = bundle.getClass(WEAPON);
		}
	}
	public static class CombinedLethalityAbilityTracker extends FlavourBuff{
		public MeleeWeapon weapon;
	};
	//Monk 3-5
	public static class CombinedEnergyAbilityTracker extends FlavourBuff{
		public boolean monkAbilused = false;
		public boolean wepAbilUsed = false;

		private static final String MONK_ABIL_USED  = "monk_abil_used";
		private static final String WEP_ABIL_USED   = "wep_abil_used";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(MONK_ABIL_USED, monkAbilused);
			bundle.put(WEP_ABIL_USED, wepAbilUsed);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			monkAbilused = bundle.getBoolean(MONK_ABIL_USED);
			wepAbilUsed = bundle.getBoolean(WEP_ABIL_USED);
		}
	}
	public static class AgressiveMovementAbilityTracker extends FlavourBuff{
		public boolean wepAbilUsed = false;
	}
	public static class QuickFollowupCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x651F66); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class QuickFollowupTracker extends FlavourBuff{};

	//Feint 4-3
	public static class CounterAbilityTacker extends FlavourBuff{};

	//gunner talent's buff
	public static class SpeedyMoveTracker extends Buff{};
	public static class BulletSavingCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x666666); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
	};
	public static class RollingTracker extends FlavourBuff{};
	public static class HonorableShotTracker extends FlavourBuff{};
	public static class SkillfulRunnerCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight( 0xFFFF00 ); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / 30); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class IntoTheShadowCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight( 0x7FA9D2 ); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / 15); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};

	//Samurai 1-3 meta
	public static class DrawEnhanceMetaTracker extends Buff {}
	//Samurai 2-3
	public static class UnexpectedSlashTracker extends Buff {}

	//Knight 1-1
	public static class ArmorEmpower extends Buff {

		{
			type = buffType.POSITIVE;
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1, 0.5f, 0);
		}

		@Override
		public float iconFadePercent() {
			float max = 1 + Dungeon.hero.pointsInTalent(Talent.TOUGH_MEAL);
			return Math.max(0, (max-left) / max);
		}

		@Override
		public String iconTextDisplay() {
			return Integer.toString(left);
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", armorBoost, left);
		}

		public int armorBoost;
		public int left;

		public void set(int armor, int hits){
			if (armor*hits > armorBoost*left) {
				armorBoost = armor;
				left = hits;
			}
		}

		public int proc(int damage) {
			damage = Math.max(0, damage-armorBoost);
			left--;
			if (left <= 0) detach();
			return damage;
		}

		private static final String BOOST = "boost";
		private static final String LEFT = "left";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( BOOST, armorBoost);
			bundle.put( LEFT, left );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			armorBoost = bundle.getInt( BOOST );
			left = bundle.getInt( LEFT );
		}

	}

	public static class KineticBattle extends Buff {

		{
			type = buffType.POSITIVE;
		}

		public float duration = 0f;
		public float maxDuration = 0f;
		public int dmgBoost = 0;

		@Override
		public int icon() {
			return BuffIndicator.WEAPON;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0, 1f, 0);
		}

		public void set() {
			dmgBoost = Math.min(5, ++dmgBoost);
			maxDuration = 1+2*hero.pointsInTalent(Talent.KINETIC_BATTLE);
			duration = maxDuration;
		}

		public int proc(int damage) {
			damage += this.dmgBoost;
			return damage;
		}

		@Override
		public boolean act() {
			if (--duration <= 0) {
				detach();
			} else {
				spend(TICK);
			}
			return true;
		}

		@Override
		public String iconTextDisplay() {
			return Integer.toString((int)duration);
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dmgBoost, duration);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (maxDuration-duration) / maxDuration);
		}

		private static final String DURATION = "duration";
		private static final String MAX_DURATION = "maxDuration";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( DURATION, duration );
			bundle.put( MAX_DURATION, maxDuration );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			duration = bundle.getFloat( DURATION );
			maxDuration = bundle.getFloat( MAX_DURATION );
		}
	}

	public static class WarCryTracker extends Buff {}

	public static class PrayForDeadTracker extends FlavourBuff {
		public static final float DURATION = 1f;
	}

	public static class JudgementTracker extends FlavourBuff {

		public static final float DURATION = 5f;

		{
			type = buffType.NEUTRAL;
			announced = true;
		}

		@Override
		public int icon() {
			return BuffIndicator.JUDGEMENT;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0xFFFF66);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public void fx(boolean on) {
			if (on) target.sprite.add(CharSprite.State.JUDGED);
			else target.sprite.remove(CharSprite.State.JUDGED);
		}

	}

	int icon;
	int maxPoints;

	// tiers 1/2/3/4 start at levels 2/7/13/21
	public static int[] tierLevelThresholds = new int[]{0, 2, 7, 13, 21, 31};

	Talent( int x, int y ){
		this(x, y, 2);
	}

	Talent( int x, int y, int maxPoints ){
		this.icon = x+TALENT_NUMBER*y;
		this.maxPoints = maxPoints;
	}

	public int icon(){
		if (this == HEROIC_ENERGY){
			int x = 43;
			int y = 0;
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					y = 0;
					break;
				case MAGE:
					y = 1;
					break;
				case ROGUE:
					y = 2;
					break;
				case HUNTRESS:
					y = 3;
					break;
				case DUELIST:
					y = 4;
					break;
				case GUNNER:
					y = 6;
					break;
				case SAMURAI:
					y = 7;
					break;
				case ADVENTURER:
					y = 8;
					break;
				case KNIGHT:
					y = 9;
					break;
				case MEDIC:
					y = 10;
					break;
			}
			if (Ratmogrify.useRatroicEnergy){
				y = 12;
			}
			return x+TALENT_NUMBER*y;
		} else {
			return icon;
		}
	}

	public int maxPoints(){
		return maxPoints;
	}

	public String title(){
		if (this == HEROIC_ENERGY && Ratmogrify.useRatroicEnergy){
			return Messages.get(this, name() + ".rat_title");
		}
		return Messages.get(this, name() + ".title");
	}

	public final String desc(){
		return desc(false);
	}

	public String desc(boolean metamorphed){
		if (metamorphed){
			String metaDesc = Messages.get(this, name() + ".meta_desc");
			if (!metaDesc.equals(Messages.NO_TEXT_FOUND)){
				return Messages.get(this, name() + ".desc") + "\n\n" + metaDesc;
			}
		}
		return Messages.get(this, name() + ".desc");
	}

	public static void onTalentUpgraded( Hero hero, Talent talent ){
		//universal
		if (talent == BETTER_CHOICE){
			switch (hero.pointsInTalent(Talent.BETTER_CHOICE)) {
				case 0: default:
					break;
				case 1:
					StoneOfEnchantment stone = new StoneOfEnchantment();
					if (stone.doPickUp( Dungeon.hero )) {
						GLog.i( Messages.get(Dungeon.hero, "you_now_have", stone.name() ));
						hero.spend(-1);
					} else {
						Dungeon.level.drop( stone, Dungeon.hero.pos ).sprite.drop();
					}
					break;
				case 2:
					ScrollOfEnchantment enchantment = new ScrollOfEnchantment();
					if (enchantment.doPickUp( Dungeon.hero )) {
						GLog.i( Messages.get(Dungeon.hero, "you_now_have", enchantment.name() ));
						hero.spend(-1);
					} else {
						Dungeon.level.drop( enchantment, Dungeon.hero.pos ).sprite.drop();
					}
					break;
				case 3:
					ScrollOfUpgrade scl = new ScrollOfUpgrade();
					if (scl.doPickUp( Dungeon.hero )) {
						GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
						hero.spend(-1);
					} else {
						Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
					}
					break;
			}
		}

		//warrior
		//for metamorphosis
		if (talent == IRON_WILL && hero.heroClass != HeroClass.WARRIOR){
			Buff.affect(hero, BrokenSeal.WarriorShield.class);
		}

		if (talent == VETERANS_INTUITION && hero.pointsInTalent(VETERANS_INTUITION) == 2){
			if (hero.belongings.armor() != null && !ShardOfOblivion.passiveIDDisabled())  {
				hero.belongings.armor.identify();
			}
		}

		if (talent == MAX_HEALTH) {
			hero.updateHT(true);
		}

		if (talent == PARRY) {
			Buff.affect(hero, ParryTracker.class);
		}



		//rouge
		if (talent == THIEFS_INTUITION && hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (hero.belongings.ring instanceof Ring && !ShardOfOblivion.passiveIDDisabled()) {
				hero.belongings.ring.identify();
			}
			if (hero.belongings.misc instanceof Ring && !ShardOfOblivion.passiveIDDisabled()) {
				hero.belongings.misc.identify();
			}
			for (Item item : Dungeon.hero.belongings){
				if (item instanceof Ring){
					((Ring) item).setKnown();
				}
			}
		}
		if (talent == THIEFS_INTUITION && hero.pointsInTalent(THIEFS_INTUITION) == 1){
			if (hero.belongings.ring instanceof Ring) hero.belongings.ring.setKnown();
			if (hero.belongings.misc instanceof Ring) ((Ring) hero.belongings.misc).setKnown();
		}

		if (talent == ADVENTURERS_INTUITION && hero.pointsInTalent(ADVENTURERS_INTUITION) == 2){
			if (hero.belongings.weapon() != null && !ShardOfOblivion.passiveIDDisabled()){
				hero.belongings.weapon().identify();
			}
		}

		if (talent == PROTECTIVE_SHADOWS && hero.invisible > 0){
			Buff.affect(hero, Talent.ProtectiveShadowsTracker.class);
		}

		if (talent == LIGHT_CLOAK && hero.heroClass == HeroClass.ROGUE){
			for (Item item : Dungeon.hero.belongings.backpack){
				if (item instanceof CloakOfShadows){
					if (!hero.belongings.lostInventory() || item.keptThroughLostInventory()) {
						((CloakOfShadows) item).activate(Dungeon.hero);
					}
				}
			}
		}

		//huntress
		if (talent == HEIGHTENED_SENSES || talent == FARSIGHT || talent == TELESCOPE || talent == DRAGONS_EYE){
			Dungeon.observe();
		}

		//duelist
		if (talent == ACCUMULATION) {
			updateQuickslot();
		}

		if (talent == TWIN_UPGRADES || talent == DESPERATE_POWER || talent == STRONGMAN){
			Item.updateQuickslot();
		}

		if (talent == UNENCUMBERED_SPIRIT && hero.pointsInTalent(talent) == 3){
			Item toGive = new ClothArmor().identify();
			if (!toGive.collect()){
				Dungeon.level.drop(toGive, hero.pos).sprite.drop();
			}
			toGive = new Gloves().identify();
			if (!toGive.collect()){
				Dungeon.level.drop(toGive, hero.pos).sprite.drop();
			}
		}


		//gunner
		if (talent == GUNNERS_INTUITION && hero.belongings.weapon instanceof Gun){
			hero.belongings.weapon.identify();
		}

		if (talent == LARGER_MAGAZINE) {
			Item.updateQuickslot();
		}

		//samurai
		if (talent == MASTERS_INTUITION) {
			if (hero.belongings.weapon instanceof MeleeWeapon && !(hero.belongings.weapon instanceof Gun) && !ShardOfOblivion.passiveIDDisabled()) {
				hero.belongings.weapon.identify();
			}
		}

		//knight
		if (talent == KNIGHTS_INTUITION) {
			if (hero.belongings.armor != null && !ShardOfOblivion.passiveIDDisabled()) {
				hero.belongings.armor.identify();
			}
		}

		if (talent == STORED_POWER) {
			BuffIndicator.refreshHero();
		}

		//adventurer
		if (talent == WATCHTOWER && hero.pointsInTalent(WATCHTOWER) > 1) {
			for (Char ch : Actor.chars()) {
				if (ch instanceof WatchTower) {
					((WatchTower) ch).updateFOV();
				}
			}
		}

		//knight
		if (talent == CALL_OF_MASTER && hero.buff(HorseRiding.RidingCooldown.class) != null) {
			hero.buff(HorseRiding.RidingCooldown.class).updateCooldown();
		}
	}

	public static class CachedRationsDropped extends CounterBuff{{revivePersists = true;}};
	public static class NatureBerriesDropped extends CounterBuff{{revivePersists = true;}};

	public static void onFoodEaten( Hero hero, float foodVal, Item foodSource ){
		if (hero.hasTalent(HEARTY_MEAL)){
			//3/5 HP healed, when hero is below 30% health
			if (hero.HP/(float)hero.HT <= 0.3f) {
				int healing = 1 + 2 * hero.pointsInTalent(HEARTY_MEAL);
				hero.HP = Math.min(hero.HP + healing, hero.HT);
				hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(healing), FloatingText.HEALING);

			}
		}
		if (hero.hasTalent(IRON_STOMACH)){
			if (hero.cooldown() > 0) {
				Buff.affect(hero, WarriorFoodImmunity.class, hero.cooldown());
			}
		}
		if (hero.hasTalent(EMPOWERING_MEAL)){
			//2/3 bonus wand damage for next 3 zaps
			Buff.affect( hero, WandEmpower.class).set(1 + hero.pointsInTalent(EMPOWERING_MEAL), 3);
			ScrollOfRecharging.charge( hero );
		}
		if (hero.hasTalent(ENERGIZING_MEAL)){
			//5/8 turns of recharging
			Buff.prolong( hero, Recharging.class, 2 + 3*(hero.pointsInTalent(ENERGIZING_MEAL)) );
			ScrollOfRecharging.charge( hero );
			SpellSprite.show(hero, SpellSprite.CHARGE);
		}
		if (hero.hasTalent(MYSTICAL_MEAL)){
			//3/5 turns of recharging
			ArtifactRecharge buff = Buff.affect( hero, ArtifactRecharge.class);
			if (buff.left() < 1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))){
				Buff.affect( hero, ArtifactRecharge.class).set(1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))).ignoreHornOfPlenty = foodSource instanceof HornOfPlenty;
			}
			ScrollOfRecharging.charge( hero );
			SpellSprite.show(hero, SpellSprite.CHARGE, 0, 1, 1);
		}
		if (hero.hasTalent(INVIGORATING_MEAL)){
			//effectively 1/2 turns of haste
			Buff.prolong( hero, Haste.class, 0.67f+hero.pointsInTalent(INVIGORATING_MEAL));
		}
		if (hero.hasTalent(STRENGTHENING_MEAL)){
			//3 bonus physical damage for next 2/3 attacks
			Buff.affect( hero, PhysicalEmpower.class).set(3, 1 + hero.pointsInTalent(STRENGTHENING_MEAL));
		}
		if (hero.hasTalent(FOCUSED_MEAL)){
			if (hero.heroClass == HeroClass.DUELIST){
				//0.67/1 charge for the duelist
				Buff.affect( hero, MeleeWeapon.Charger.class ).gainCharge((hero.pointsInTalent(FOCUSED_MEAL)+1)/3f);
				ScrollOfRecharging.charge( hero );
			} else {
				// lvl/3 / lvl/2 bonus dmg on next hit for other classes
				Buff.affect( hero, PhysicalEmpower.class).set(Math.round(hero.lvl / (4f - hero.pointsInTalent(FOCUSED_MEAL))), 1);
			}
		}
		if (hero.hasTalent(Talent.RELOADING_MEAL)) {
			if (hero.belongings.weapon instanceof Gun) {
				((Gun)hero.belongings.weapon).quickReload();
				if (hero.pointsInTalent(Talent.RELOADING_MEAL) > 1) {
					((Gun)hero.belongings.weapon).manualReload(1, true);
				}
			}
		}
		if (hero.hasTalent(Talent.INFINITE_BULLET_MEAL)) {
			Buff.affect(hero, InfiniteBullet.class, 1+hero.pointsInTalent(Talent.INFINITE_BULLET_MEAL));
		}
		if (hero.hasTalent(Talent.CRITICAL_MEAL)) {
			Buff.affect(hero, Sheath.CertainCrit.class).set(hero.pointsInTalent(Talent.CRITICAL_MEAL));
		}
		if (hero.hasTalent(Talent.NATURES_MEAL)) {
			if (hero.pointsInTalent(Talent.NATURES_MEAL) == 1) {
				for (int i : PathFinder.NEIGHBOURS4) {
					int c = Dungeon.level.map[hero.pos + i];
					if (c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS) {
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get(hero.pos + i).burst(LeafParticle.LEVEL_SPECIFIC, 4);
					}
				}
			} else {
				for (int i : PathFinder.NEIGHBOURS8) {
					int c = Dungeon.level.map[hero.pos + i];
					if (c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS) {
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get(hero.pos + i).burst(LeafParticle.LEVEL_SPECIFIC, 4);
					}
				}
			}
		}
		if (hero.hasTalent(Talent.TOUGH_MEAL)) {
			Buff.affect(hero, ArmorEmpower.class).set(3, 1+hero.pointsInTalent(Talent.TOUGH_MEAL));
		}
		if (hero.hasTalent(Talent.IMPREGNABLE_MEAL)) {
			Buff.affect(hero, ArmorEnhance.class).set(hero.pointsInTalent(Talent.IMPREGNABLE_MEAL), 15f);
		}
	}

	public static class WarriorFoodImmunity extends FlavourBuff{
		{ actPriority = HERO_PRIO+1; }
	}

	public static float itemIDSpeedFactor( Hero hero, Item item ){
		// 1.75x/2.5x speed with Huntress talent
		float factor = 1f + 0.75f*hero.pointsInTalent(SURVIVALISTS_INTUITION);

		// Affected by both Warrior(1.75x/2.5x) and Duelist(2.5x/inst.) talents
		if (item instanceof MeleeWeapon){
			factor *= 1f + 1.5f*hero.pointsInTalent(ADVENTURERS_INTUITION); //instant at +2 (see onItemEquipped)
			factor *= 1f + 0.75f*hero.pointsInTalent(VETERANS_INTUITION);
		}
		// Affected by both Warrior(2.5x/inst.) and Duelist(1.75x/2.5x) talents
		if (item instanceof Armor){
			factor *= 1f + 0.75f*hero.pointsInTalent(ADVENTURERS_INTUITION);
			factor *= 1f + hero.pointsInTalent(VETERANS_INTUITION); //instant at +2 (see onItemEquipped)
		}
		// 3x/instant for Mage (see Wand.wandUsed())
		if (item instanceof Wand){
			factor *= 1f + 2.0f*hero.pointsInTalent(SCHOLARS_INTUITION);
		}
		// 2x/instant for Rogue (see onItemEqupped), also id's type on equip/on pickup
		if (item instanceof Ring){
			factor *= 1f + hero.pointsInTalent(THIEFS_INTUITION);
		}
		return factor;
	}

	public static void onPotionUsed( Hero hero, int cell, float factor ){
		if (hero.hasTalent(LIQUID_WILLPOWER)){
			if (hero.heroClass == HeroClass.WARRIOR) {
				BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
				if (shield != null) {
					// 50/75% of total shield
					int shieldToGive = Math.round(factor * shield.maxShield() * 0.25f * (1 + hero.pointsInTalent(LIQUID_WILLPOWER)));
					hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(shieldToGive), FloatingText.SHIELDING);
					shield.supercharge(shieldToGive);
				}
			} else {
				// 5/7.5% of max HP
				int shieldToGive = Math.round( factor * hero.HT * (0.025f * (1+hero.pointsInTalent(LIQUID_WILLPOWER))));
				hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(shieldToGive), FloatingText.SHIELDING);
				Buff.affect(hero, Barrier.class).setShield(shieldToGive);
			}
		}
		if (hero.hasTalent(LIQUID_NATURE)){
			ArrayList<Integer> grassCells = new ArrayList<>();
			for (int i : PathFinder.NEIGHBOURS9){
				grassCells.add(cell+i);
			}
			Random.shuffle(grassCells);
			for (int grassCell : grassCells){
				Char ch = Actor.findChar(grassCell);
				if (ch != null && ch.alignment == Char.Alignment.ENEMY){
					//1/2 turns of roots
					Buff.affect(ch, Roots.class, factor * hero.pointsInTalent(LIQUID_NATURE));
				}
				if (Dungeon.level.map[grassCell] == Terrain.EMPTY ||
						Dungeon.level.map[grassCell] == Terrain.EMBERS ||
						Dungeon.level.map[grassCell] == Terrain.EMPTY_DECO){
					Level.set(grassCell, Terrain.GRASS);
					GameScene.updateMap(grassCell);
				}
				CellEmitter.get(grassCell).burst(LeafParticle.LEVEL_SPECIFIC, 4);
			}
			// 4/6 cells total
			int totalGrassCells = (int) (factor * (2 + 2 * hero.pointsInTalent(LIQUID_NATURE)));
			while (grassCells.size() > totalGrassCells){
				grassCells.remove(0);
			}
			for (int grassCell : grassCells){
				int t = Dungeon.level.map[grassCell];
				if ((t == Terrain.EMPTY || t == Terrain.EMPTY_DECO || t == Terrain.EMBERS
						|| t == Terrain.GRASS || t == Terrain.FURROWED_GRASS)
						&& Dungeon.level.plants.get(grassCell) == null){
					Level.set(grassCell, Terrain.HIGH_GRASS);
					GameScene.updateMap(grassCell);
				}
			}
			Dungeon.observe();
		}
		if (hero.hasTalent(LIQUID_AGILITY)){
			Buff.prolong(hero, LiquidAgilEVATracker.class, hero.cooldown() + Math.max(0, factor-1));
			if (factor >= 0.5f){
				Buff.prolong(hero, LiquidAgilACCTracker.class, 5f).uses = Math.round(factor);
			}
		}
		if (hero.hasTalent(PHARMACEUTICS)) {
			hero.heal(Math.round(factor*(2+3*hero.pointsInTalent(PHARMACEUTICS))));
		}
	}

	public static void onScrollUsed( Hero hero, int pos, float factor ){
		if (hero.hasTalent(INSCRIBED_POWER)){
			// 2/3 empowered wand zaps
			Buff.affect(hero, ScrollEmpower.class).reset((int) (factor * (1 + hero.pointsInTalent(INSCRIBED_POWER))));
		}
		if (hero.hasTalent(INSCRIBED_STEALTH)){
			// 3/5 turns of stealth
			Buff.affect(hero, Invisibility.class, factor * (1 + 2*hero.pointsInTalent(INSCRIBED_STEALTH)));
			Sample.INSTANCE.play( Assets.Sounds.MELD );
		}
		if (hero.hasTalent(Talent.MAGIC_RUSH)) {
			MagesStaff staff = hero.belongings.getItem(MagesStaff.class);
			if (staff != null) {
				staff.gainCharge(factor * hero.pointsInTalent(Talent.MAGIC_RUSH), false);
				ScrollOfRecharging.charge(hero);
			}
		}
		if (hero.hasTalent(Talent.INSCRIBED_BULLET)) {
			//collects 5/10 bullet
			BulletItem bulletItem = new BulletItem();
			bulletItem.quantity((int)(factor * 5 * hero.pointsInTalent(Talent.INSCRIBED_BULLET)));
			bulletItem.doPickUp(hero);
		}
		if (hero.hasTalent(Talent.INSCRIBED_LETHALITY)) {
			Buff.affect(hero, Sheath.CertainCrit.class).set((int)(factor * hero.pointsInTalent(Talent.INSCRIBED_LETHALITY)));
		}
		if (hero.hasTalent(Talent.SMITHING_SPELL)) {
			Buff.affect(hero, WeaponEnhance.class).set(hero.pointsInTalent(Talent.SMITHING_SPELL), 10f*factor);
			Buff.affect(hero, ArmorEnhance.class).set(hero.pointsInTalent(Talent.SMITHING_SPELL), 10f*factor);
		}
	}

	public static void onUpgradeScrollUsed( Hero hero ){
		if (hero.hasTalent(INSCRIBED_POWER)){
			if (hero.heroClass == HeroClass.MAGE) {
				MagesStaff staff = hero.belongings.getItem(MagesStaff.class);
				if (staff != null) {
					staff.gainCharge(2 + 2 * hero.pointsInTalent(INSCRIBED_POWER), true);
					ScrollOfRecharging.charge(Dungeon.hero);
					SpellSprite.show(hero, SpellSprite.CHARGE);
				}
			} else {
				Buff.affect(hero, Recharging.class, 4 + 8 * hero.pointsInTalent(INSCRIBED_POWER));
			}
		}
	}

	public static void onArtifactUsed( Hero hero ){
		if (hero.hasTalent(ENHANCED_RINGS)){
			Buff.prolong(hero, EnhancedRings.class, 3f*hero.pointsInTalent(ENHANCED_RINGS));
		}
	}

	public static void onItemEquipped( Hero hero, Item item ){
		boolean identify = false;
		if (hero.pointsInTalent(VETERANS_INTUITION) == 2 && item instanceof Armor){
			identify = true;
		}
		if (hero.hasTalent(THIEFS_INTUITION) && item instanceof Ring){
			if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
				identify = true;
			}
			((Ring) item).setKnown();
		}
		if (hero.pointsInTalent(ADVENTURERS_INTUITION) == 2 && item instanceof Weapon){
			identify = true;
		}

		if (identify && !ShardOfOblivion.passiveIDDisabled()){
			item.identify();
		}
		if (hero.hasTalent(GUNNERS_INTUITION) && item instanceof Gun) {
			item.identify();
		}
		if (hero.hasTalent(MASTERS_INTUITION) && item instanceof MeleeWeapon && !(item instanceof Gun)) {
			item.identify();
		}
		if (hero.hasTalent(KNIGHTS_INTUITION) && item instanceof Armor) {
			item.identify();
		}
	}

	public static void onItemCollected( Hero hero, Item item ){
		if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (item instanceof Ring) ((Ring) item).setKnown();
		}
		if (hero.pointsInTalent(GUNNERS_INTUITION) == 2 && item instanceof Gun) {
			item.cursedKnown = true;
		}
		if (hero.pointsInTalent(MASTERS_INTUITION) == 2 && item instanceof MeleeWeapon && !(item instanceof Gun)) {
			item.cursedKnown = true;
		}
		if (hero.pointsInTalent(KNIGHTS_INTUITION) == 2 && item instanceof Armor) {
			item.cursedKnown = true;
		}
	}

	//note that IDing can happen in alchemy scene, so be careful with VFX here
	public static void onItemIdentified( Hero hero, Item item ){
		//currently no talents that trigger here, it wasn't a very popular trigger =(
	}

	public static int onAttackProc( Hero hero, Char enemy, int dmg ){

		if (hero.hasTalent(Talent.PROVOKED_ANGER)
			&& hero.buff(ProvokedAngerTracker.class) != null){
			dmg += 1 + hero.pointsInTalent(Talent.PROVOKED_ANGER);
			hero.buff(ProvokedAngerTracker.class).detach();
		}

		if (hero.hasTalent(Talent.LINGERING_MAGIC)
				&& hero.buff(LingeringMagicTracker.class) != null){
			dmg += Random.IntRange(hero.pointsInTalent(Talent.LINGERING_MAGIC) , 2);
			hero.buff(LingeringMagicTracker.class).detach();
		}

		if (hero.hasTalent(Talent.SUCKER_PUNCH)
				&& enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
				&& enemy.buff(SuckerPunchTracker.class) == null){
			dmg += Random.IntRange(hero.pointsInTalent(Talent.SUCKER_PUNCH) , 2);
			Buff.affect(enemy, SuckerPunchTracker.class);
		}

		if (hero.hasTalent(Talent.FOLLOWUP_STRIKE) && enemy.isAlive() && enemy.alignment == Char.Alignment.ENEMY) {
			if (hero.belongings.attackingWeapon() instanceof MissileWeapon) {
				Buff.prolong(hero, FollowupStrikeTracker.class, 5f).object = enemy.id();
			} else if (hero.buff(FollowupStrikeTracker.class) != null
					&& hero.buff(FollowupStrikeTracker.class).object == enemy.id()){
				dmg += 1 + hero.pointsInTalent(FOLLOWUP_STRIKE);
				if (hero.belongings.weapon == null && hero.subClass == HeroSubClass.FIGHTER) {
					Buff.affect( enemy, Paralysis.class, 1f );
				}
				hero.buff(FollowupStrikeTracker.class).detach();
			}
		}

		if (hero.buff(Talent.SpiritBladesTracker.class) != null
				&& Random.Int(10) < 3*hero.pointsInTalent(Talent.SPIRIT_BLADES)){
			SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
			if (bow != null) dmg = bow.proc( hero, enemy, dmg );
			hero.buff(Talent.SpiritBladesTracker.class).detach();
		}

		if (hero.hasTalent(PATIENT_STRIKE)){
			if (hero.buff(PatientStrikeTracker.class) != null
					&& !(hero.belongings.attackingWeapon() instanceof MissileWeapon)){
				hero.buff(PatientStrikeTracker.class).detach();
				dmg += Random.IntRange(hero.pointsInTalent(Talent.PATIENT_STRIKE), 2);
			}
		}

		if (hero.hasTalent(DEADLY_FOLLOWUP) && enemy.alignment == Char.Alignment.ENEMY) {
			if (hero.belongings.attackingWeapon() instanceof MissileWeapon) {
				if (!(hero.belongings.attackingWeapon() instanceof SpiritBow.SpiritArrow)) {
					Buff.prolong(hero, DeadlyFollowupTracker.class, 5f).object = enemy.id();
				}
			} else if (hero.buff(DeadlyFollowupTracker.class) != null
					&& hero.buff(DeadlyFollowupTracker.class).object == enemy.id()){
				dmg = Math.round(dmg * (1.0f + .1f*hero.pointsInTalent(DEADLY_FOLLOWUP)));
			}
		}

		if (hero.hasTalent(DRAWING_ENHANCE) && hero.buff(Sheath.Sheathing.class) != null) {
			dmg += 1+hero.pointsInTalent(DRAWING_ENHANCE);
		}

		if (hero.heroClass != HeroClass.SAMURAI && hero.hasTalent(DRAWING_ENHANCE) && enemy.buff(Talent.DrawEnhanceMetaTracker.class) == null ) {
			dmg += Hero.heroDamageIntRange(hero.pointsInTalent(Talent.DRAWING_ENHANCE), 2);
			Buff.affect(enemy, Talent.DrawEnhanceMetaTracker.class);
		}

		if (hero.hasTalent(Talent.WATER_FRIENDLY) && Dungeon.level.map[hero.pos] == Terrain.WATER) {
			dmg += Hero.heroDamageIntRange(1, hero.pointsInTalent(Talent.WATER_FRIENDLY));
			Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
		}

		if (hero.buff(Talent.SkilledHandTracker.class) != null) {
			dmg += 1+hero.pointsInTalent(Talent.SKILLED_HAND);
			hero.buff(Talent.SkilledHandTracker.class).detach();
			Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
		}

		if (hero.hasTalent(Talent.NATURE_FRIENDLY) && (level.map[hero.pos] == Terrain.HIGH_GRASS || level.map[hero.pos] == Terrain.FURROWED_GRASS)) {
			dmg += Random.Int(1, hero.pointsInTalent(Talent.NATURE_FRIENDLY));
		}

		if (hero.buff(Talent.KineticBattle.class) != null) {
			dmg = hero.buff(Talent.KineticBattle.class).proc(dmg);
		}

		if (hero.hasTalent(Talent.TACKLE) && level.adjacent(enemy.pos, hero. pos) && hero.belongings.armor != null && (hero.belongings.attackingWeapon() instanceof MeleeWeapon || (hero.belongings.attackingWeapon() == null))) {
			dmg += hero.belongings.armor.DRMax()*0.05f*hero.pointsInTalent(Talent.TACKLE);
		}

		//attacking procs
		if (hero.hasTalent(SPEEDY_MOVE) && enemy instanceof Mob && enemy.buff(SpeedyMoveTracker.class) == null){
			Buff.affect(enemy, SpeedyMoveTracker.class);
			Buff.prolong(hero, Haste.class, 1f + hero.pointsInTalent(SPEEDY_MOVE));
		}

		if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
			if (hero.hasTalent(Talent.POISONOUS_BLADE)) {
				Buff.affect(enemy, Poison.class).set(2+hero.pointsInTalent(Talent.POISONOUS_BLADE));
			}
			if (hero.hasTalent(Talent.SOUL_COLLECT) && dmg >= enemy.HP) {
				int healAmt = 3*hero.pointsInTalent(Talent.SOUL_COLLECT);
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 2 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}
			if (hero.hasTalent(Talent.TRAIL_TRACKING) && dmg >= enemy.HP) {
				Buff.affect(hero, MindVision.class, hero.pointsInTalent(Talent.TRAIL_TRACKING));
			}

			if (hero.pointsInTalent(Talent.MASTER_OF_CLOAKING) == 3) {
				if (hero.buff(Talent.ChaseCooldown.class) != null) {
					hero.buff(Talent.ChaseCooldown.class).spendTime();
				}
				if (hero.buff(Talent.ChainCooldown.class) != null) {
					hero.buff(Talent.ChainCooldown.class).spendTime();
				}
				if (hero.buff(Talent.LethalCooldown.class) != null) {
					hero.buff(Talent.LethalCooldown.class).spendTime();
				}
			}
		}

		if (hero.hasTalent(Talent.WAR_CRY) && enemy.buff(WarCryTracker.class) == null) {
			Buff.affect(enemy, WarCryTracker.class);
			Buff.prolong(hero, Adrenaline.class, 1+hero.pointsInTalent(Talent.WAR_CRY));
		}

		if (hero.hasTalent(Talent.BLOOMING_WEAPON)
				&& Random.Float() < 0.05f*hero.pointsInTalent(Talent.BLOOMING_WEAPON)) {
			boolean secondPlant = Random.Float() <= 0.33f;
			ArrayList<Integer> positions = new ArrayList<>();
			Blooming blooming = new Blooming();
			for (int i : PathFinder.NEIGHBOURS8){
				positions.add(i);
			}
			Random.shuffle( positions );
			for (int i : positions){
				if (blooming.plantGrass(enemy.pos + i)) {
					if (secondPlant) secondPlant = false;
					else break;
				}
			}
		}

		if (hero.hasTalent(Talent.ANTI_DEMON) &&
				hero.buff(Bless.class) != null &&
				(Char.hasProp(enemy, Char.Property.DEMONIC) || Char.hasProp(enemy, Char.Property.UNDEAD))) {
			dmg *= 1f+hero.pointsInTalent(Talent.ANTI_DEMON)/3f;
		}

		if (hero.hasTalent(Talent.ARMY_OF_DEATH)) {
			float procChance = 0.1f * hero.pointsInTalent(Talent.ARMY_OF_DEATH);
			if (dmg >= enemy.HP
					&& Random.Float() < procChance
					&& !enemy.isImmune(Corruption.class)
					&& enemy.buff(Corruption.class) == null
					&& enemy instanceof Mob
					&& enemy.isAlive()){
				Corruption.corruptionHeal(enemy);
				AllyBuff.affectAndLoot((Mob) enemy, hero, Corruption.class);
				dmg = 0;
			}

		}
		return dmg;
	}

	public static void onLevelUp() {
		if (hero.hasTalent(Talent.FLAG_OF_CONQUEST)) { //deals 50/75% of enemies' maximum health who are in hero's sight
			for (Char ch : Actor.chars()) {
				if (level.heroFOV[ch.pos] && ch.alignment == Char.Alignment.ENEMY && !ch.properties().contains(Char.Property.BOSS) && !ch.properties().contains(Char.Property.MINIBOSS)){
					ch.sprite.emitter().burst(ShadowParticle.UP, 10);
					ch.damage(Math.round(0.25f*(1+hero.pointsInTalent(Talent.FLAG_OF_CONQUEST))*ch.HT), hero);
					GameScene.flash(0x30FFFF40);
					Sample.INSTANCE.play(Assets.Sounds.BLAST);
				}
			}
			TalentSprite.show(hero, FLAG_OF_CONQUEST);
		}

		if (hero.buff(HorseRiding.class) != null) {
			hero.buff(HorseRiding.class).onLevelUp();
		}
	}

	public static class ParryCooldown extends Buff{
		float cooldown;
		public void set() {
			cooldown = 90-20*hero.pointsInTalent(Talent.PARRY)+1;
		}
		@Override
		public boolean act() {
			cooldown -= Actor.TICK;
			if (cooldown <= 0) {
				Buff.affect(target, ParryTracker.class);
				detach();
			}
			if (!hero.hasTalent(Talent.PARRY)) {
				detach();
			}
			spend(Actor.TICK);
			return true;
		}
	};

	public static class ParryTracker extends Buff{
		{ actPriority = HERO_PRIO+1;}

		{
			type = buffType.NEUTRAL;
			announced = true;
		}

		@Override
		public int icon() {
			return BuffIndicator.PARRY;
		}

		@Override
		public boolean act() {
			if (!hero.hasTalent(Talent.PARRY)) {
				detach();
			}
			spend(Actor.TICK);
			return true;
		}

		@Override
		public void detach() {
			super.detach();
			Buff.affect(target, ParryCooldown.class).set();
		}
	};
	public static class RiposteTracker extends Buff {
		{
			actPriority = VFX_PRIO;
		}

		public Char enemy;

		@Override
		public boolean act() {
			target.sprite.attack(enemy.pos, new Callback() {
				@Override
				public void call() {
					AttackIndicator.target(enemy);
					if (hero.attack(enemy, 1f, 0, 1)) {
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					}

					next();
				}
			});
			detach();
			return false;
		}
	}
	public static class ProvokedAngerTracker extends FlavourBuff{
		{ type = Buff.buffType.POSITIVE; }
		public int icon() { return BuffIndicator.WEAPON; }
		public void tintIcon(Image icon) { icon.hardlight(1.43f, 1.43f, 1.43f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }
	}
	public static class LingeringMagicTracker extends FlavourBuff{
		{ type = Buff.buffType.POSITIVE; }
		public int icon() { return BuffIndicator.WEAPON; }
		public void tintIcon(Image icon) { icon.hardlight(1.43f, 1.43f, 0f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }
	}
	public static class SuckerPunchTracker extends Buff{};
	public static class FollowupStrikeTracker extends FlavourBuff{
		public int object;
		{ type = Buff.buffType.POSITIVE; }
		public int icon() { return BuffIndicator.INVERT_MARK; }
		public void tintIcon(Image icon) { icon.hardlight(0f, 0.75f, 1f); }
		public float iconFadePercent() { return Math.max(0, 1f - (visualcooldown() / 5)); }
		private static final String OBJECT    = "object";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(OBJECT, object);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			object = bundle.getInt(OBJECT);
		}
	};

	public static final int MAX_TALENT_TIERS = 4;

	public static void initClassTalents( Hero hero ){
		initClassTalents( hero.heroClass, hero.talents, hero.metamorphedTalents );
	}

	public static void initClassTalents( HeroClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents){
		initClassTalents( cls, talents, new LinkedHashMap<>());
	}

	public static void initClassTalents( HeroClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents, LinkedHashMap<Talent, Talent> replacements ){
		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		ArrayList<Talent> tierTalents = new ArrayList<>();

		//tier 1
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HEARTY_MEAL, VETERANS_INTUITION, PROVOKED_ANGER, IRON_WILL, MAX_HEALTH);
				break;
			case MAGE:
				Collections.addAll(tierTalents, EMPOWERING_MEAL, SCHOLARS_INTUITION, LINGERING_MAGIC, BACKUP_BARRIER, CHARGE_PRESERVE);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, CACHED_RATIONS, THIEFS_INTUITION, SUCKER_PUNCH, PROTECTIVE_SHADOWS, EMERGENCY_ESCAPE);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, NATURES_BOUNTY, SURVIVALISTS_INTUITION, FOLLOWUP_STRIKE, NATURES_AID, WATER_FRIENDLY);
				break;
			case DUELIST:
				Collections.addAll(tierTalents, STRENGTHENING_MEAL, ADVENTURERS_INTUITION, PATIENT_STRIKE, AGGRESSIVE_BARRIER, SKILLED_HAND);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, RELOADING_MEAL, GUNNERS_INTUITION, SPEEDY_MOVE, SAFE_RELOAD, CLOSE_COMBAT);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents, BASIC_PRACTICE, MASTERS_INTUITION, DRAWING_ENHANCE, PARRING, ADRENALINE_SURGE);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, NATURE_FRIENDLY, SAFE_POTION, ROOT, PLANT_BARRIER, ROPE_MAKING);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents, TOUGH_MEAL, KNIGHTS_INTUITION, KINETIC_BATTLE, HARD_SHIELD, WAR_CRY	);
				break;
			case MEDIC:
				Collections.addAll(tierTalents, SCAR_ATTACK, DOCTORS_INTUITION, PRECISION_HANDSKILL, FIRST_AID_TREAT, BREAKTHROUGH);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(0).put(talent, 0);
		}
		tierTalents.clear();

		//tier 2
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, IRON_STOMACH, LIQUID_WILLPOWER, RUNIC_TRANSFERENCE, LETHAL_MOMENTUM, IMPROVISED_PROJECTILES, PARRY);
				break;
			case MAGE:
				Collections.addAll(tierTalents, ENERGIZING_MEAL, INSCRIBED_POWER, WAND_PRESERVATION, ARCANE_VISION, SHIELD_BATTERY, FASTER_CHARGER);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, MYSTICAL_MEAL, INSCRIBED_STEALTH, WIDE_SEARCH, SILENT_STEPS, ROGUES_FORESIGHT, MOVESPEED_ENHANCE);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, INVIGORATING_MEAL, LIQUID_NATURE, REJUVENATING_STEPS, HEIGHTENED_SENSES, DURABLE_PROJECTILES, ADDED_MEAL);
				break;
			case DUELIST:
				Collections.addAll(tierTalents, FOCUSED_MEAL, LIQUID_AGILITY, WEAPON_RECHARGING, LETHAL_HASTE, SWIFT_EQUIP, ACCUMULATION);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, INFINITE_BULLET_MEAL, INSCRIBED_BULLET, MIND_VISION, CAMOUFLAGE, LARGER_MAGAZINE, BULLET_COLLECT);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents, CRITICAL_MEAL, INSCRIBED_LETHALITY, UNEXPECTED_SLASH, DRAGONS_EYE, WEAPON_MASTERY, CRITICAL_THROW);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, NATURES_MEAL, PHARMACEUTICS, HERB_EXTRACTION, FIREWATCH, ROPE_REBOUND, WEAKENING_POISON);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents, IMPREGNABLE_MEAL, SMITHING_SPELL, ARMOR_ADAPTION, CHIVALRY, PROTECTION, FLAG_OF_CONQUEST);
				break;
			case MEDIC:
				Collections.addAll(tierTalents, HEALING_MEAL, RECYCLING, HIGH_POWER, RADIATION, STRONG_HEALPOWER, DIET);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(1).put(talent, 0);
		}
		tierTalents.clear();

		//tier 3
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HOLD_FAST, STRONGMAN);
				break;
			case MAGE:
				Collections.addAll(tierTalents, DESPERATE_POWER, ALLY_WARP);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, ENHANCED_RINGS, LIGHT_CLOAK);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, POINT_BLANK, SEER_SHOT);
				break;
			case DUELIST:
				Collections.addAll(tierTalents, PRECISE_ASSAULT, DEADLY_FOLLOWUP);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, STREET_BATTLE, FAST_RELOAD);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents, QUICK_SHEATHING, LETHAL_POWER);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, LONG_MACHETE, BLOOMING_WEAPON);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents, CRAFTMANS_SKILLS, TACKLE);
				break;
			case MEDIC:
				Collections.addAll(tierTalents, STRONG_NEXUS, TARGET_SET);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(2).put(talent, 0);
		}
		tierTalents.clear();

		//tier4
		//TBD
	}

	public static void initSubclassTalents( Hero hero ){
		initSubclassTalents( hero.subClass, hero.talents );
	}

	public static void initSubclassTalents( HeroSubClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents ){
		if (cls == HeroSubClass.NONE) return;

		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		ArrayList<Talent> tierTalents = new ArrayList<>();

		Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE );
		//tier 3
		switch (cls){
			case BERSERKER: default:
				Collections.addAll(tierTalents, ENDLESS_RAGE, DEATHLESS_FURY, ENRAGED_CATALYST, LETHAL_RAGE, MAX_RAGE, ENDURANCE );
				break;
			case GLADIATOR:
				Collections.addAll(tierTalents, CLEAVE, LETHAL_DEFENSE, ENHANCED_COMBO, LIGHT_WEAPON, OFFENSIVE_DEFENSE, SKILL_REPEAT );
				break;
			case VETERAN:
				Collections.addAll(tierTalents, POWERFUL_TACKLE, MYSTICAL_TACKLE, DELAYED_GRENADE, INCAPACITATION, SUPER_ARMOR, IMPROVED_TACKLE	);
				break;
			case BATTLEMAGE:
				Collections.addAll(tierTalents, EMPOWERED_STRIKE, MYSTICAL_CHARGE, EXCESS_CHARGE, BATTLE_MAGIC, MAGIC_RUSH, MAGICAL_CIRCLE );
				break;
			case WARLOCK:
				Collections.addAll(tierTalents, SOUL_EATER, SOUL_SIPHON, NECROMANCERS_MINIONS, MADNESS, ENHANCED_MARK, MARK_OF_WEAKNESS );
				break;
			case WIZARD:
				Collections.addAll(tierTalents, SPELL_ENHANCE, BRIG_BOOST, ENERGY_REMAINS, MAGIC_EMPOWER, SECOND_EFFECT, LIFE_ENERGY );
				break;
			case ASSASSIN:
				Collections.addAll(tierTalents, ENHANCED_LETHALITY, ASSASSINS_REACH, BOUNTY_HUNTER, ENERGY_DRAW, PERFECT_ASSASSIN, CAUTIOUS_PREP );
				break;
			case FREERUNNER:
				Collections.addAll(tierTalents, EVASIVE_ARMOR, PROJECTILE_MOMENTUM, SPEEDY_STEALTH, QUICK_PREP, OVERCOMING, MOMENTARY_FOCUSING );
				break;
			case CHASER:
				Collections.addAll(tierTalents, POISONOUS_BLADE, LETHAL_SURPRISE, CHAIN_CLOCK, SOUL_COLLECT, TRAIL_TRACKING, MASTER_OF_CLOAKING );
				break;
			case SNIPER:
				Collections.addAll(tierTalents, FARSIGHT, SHARED_ENCHANTMENT, SHARED_UPGRADES, KICK, SHOOTING_EYES, TARGET_SPOTTING );
				break;
			case WARDEN:
				Collections.addAll(tierTalents, DURABLE_TIPS, BARKSKIN, SHIELDING_DEW, LIVING_GRASS, ATTRACTION, HEALING_DEW );
				break;
			case FIGHTER:
				Collections.addAll(tierTalents, SWIFT_MOVEMENT, LESS_RESIST, RING_KNUCKLE, MYSTICAL_PUNCH, QUICK_STEP, COUNTER_ATTACK );
				break;
			case CHAMPION:
				Collections.addAll(tierTalents, VARIED_CHARGE, TWIN_UPGRADES, COMBINED_LETHALITY, FASTER_CHARGE, QUICK_FOLLOWUP, TWIN_SWORD );
				break;
			case MONK:
				Collections.addAll(tierTalents, UNENCUMBERED_SPIRIT, MONASTIC_VIGOR, COMBINED_ENERGY, RESTORED_ENERGY, ENERGY_BARRIER, HARMONY );
				break;
			case FENCER:
				Collections.addAll(tierTalents, CLAM_STEPS, CRITICAL_MOMENTUM, KINETIC_MOVEMENT, AGGRESSIVE_MOVEMENT, UNENCUMBERED_MOVEMENT, SOULIZE );
				break;
			case OUTLAW:
				Collections.addAll(tierTalents, ROLLING, PERFECT_FOCUSING, HONORABLE_SHOT, BULLET_TIME, INEVITABLE_DEATH, HEADSHOT );
				break;
			case GUNSLINGER:
				Collections.addAll(tierTalents, QUICK_RELOAD, MOVING_SHOT, ELEMENTAL_BULLET, IMPROVISATION, SOUL_BULLET, LIGHT_MOVEMENT );
				break;
			case SPECIALIST:
				Collections.addAll(tierTalents, STEALTH_MASTER, SKILLFUL_RUNNER, STEALTH, INTO_THE_SHADOW, RANGED_SNIPING, TELESCOPE );
				break;
			case SLASHER:
				Collections.addAll(tierTalents, MIND_FOCUSING, STORED_POWER, ARCANE_POWER, ENERGY_COLLECT, DIVIDED_POWER, WIND_BLAST );
				break;
			case MASTER:
				Collections.addAll(tierTalents, ENHANCED_CRIT, POWERFUL_SLASH, STATIC_PREPARATION, ACCELERATION, INNER_EYE, DYNAMIC_PREPARATION );
				break;
			case SLAYER:
				Collections.addAll(tierTalents, AFTERIMAGE, FASTER_THAN_LIGHT, QUICK_RECOVER, HASTE_EVASION, ACCELERATED_LETHALITY, STABLE_BARRIER );
				break;
			case ENGINEER:
				Collections.addAll(tierTalents, BARRICADE, WIRE, WATCHTOWER, CANNON, MACHINEGUN, MORTAR);
				break;
			case EXPLORER:
				Collections.addAll(tierTalents, JUNGLE_EXPLORE, DURABLE_ROPE, LASSO, ROPE_COLLECTOR, ROPE_MASTER, VINE_BIND );
				break;
			case RESEARCHER:
				Collections.addAll(tierTalents, BIOLOGY_PROJECT, RAPID_GROWTH, BIO_ENERGY, WATER_ABSORB, POWERFUL_ACID, STICKY_OOZE );
				break;
			case DEATHKNIGHT:
				Collections.addAll(tierTalents, ARMY_OF_DEATH, DEATHS_CHILL, OVERCOME, RESENTMENT, UNDEAD, DEATHS_FEAR );
				break;
			case HORSEMAN:
				Collections.addAll(tierTalents, SHOCKWAVE, CALL_OF_MASTER, DASH_ENHANCE, BUFFER, PARKOUR, PILOTING);
				break;
			case CRUSADER:
				Collections.addAll(tierTalents, HOLY_SHIELD, PRAY_FOR_DEAD, JUDGEMENT, CLEANSING_PRAY, PUNISHMENT, ANTI_DEMON);
				break;
			case SAVIOR:
				Collections.addAll(tierTalents, RECRUIT, DELAYED_HEALING, APPEASE, ADRENALINE, STIMPACK, MEDICAL_RAY);
				break;
			case THERAPIST:
				Collections.addAll(tierTalents, OINTMENT, COMPRESS_BANDAGE, ANTIBIOTICS, QUICK_PREPARE, SPLINT, DEFIBRILLATOR);
				break;
			case MEDICALOFFICER:
				Collections.addAll(tierTalents, MOVE_ORDER, REINFORCE_ORDER, ENGINEER_ORDER, STANCE_ORDER, ATTACK_ORDER, CAS_ORDER);
				break;
		}
		for (Talent talent : tierTalents){
			talents.get(2).put(talent, 0);
		}
		tierTalents.clear();

	}

	public static void initArmorTalents( Hero hero ){
		initArmorTalents( hero.armorAbility, hero.talents);
	}

	public static void initArmorTalents(ArmorAbility abil, ArrayList<LinkedHashMap<Talent, Integer>> talents ){
		if (abil == null) return;

		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		for (Talent t : abil.talents()){
			talents.get(3).put(t, 0);
		}
	}

	private static final String TALENT_TIER = "talents_tier_";

	public static void storeTalentsInBundle( Bundle bundle, Hero hero ){
		for (int i = 0; i < MAX_TALENT_TIERS; i++){
			LinkedHashMap<Talent, Integer> tier = hero.talents.get(i);
			Bundle tierBundle = new Bundle();

			for (Talent talent : tier.keySet()){
				if (tier.get(talent) > 0){
					tierBundle.put(talent.name(), tier.get(talent));
				}
				if (tierBundle.contains(talent.name())){
					tier.put(talent, Math.min(tierBundle.getInt(talent.name()), talent.maxPoints()));
				}
			}
			bundle.put(TALENT_TIER+(i+1), tierBundle);
		}

		Bundle replacementsBundle = new Bundle();
		for (Talent t : hero.metamorphedTalents.keySet()){
			replacementsBundle.put(t.name(), hero.metamorphedTalents.get(t));
		}
		bundle.put("replacements", replacementsBundle);
	}

	private static final HashSet<String> removedTalents = new HashSet<>();
	static{
		//v2.4.0
		removedTalents.add("TEST_SUBJECT");
		removedTalents.add("TESTED_HYPOTHESIS");
		//v2.2.0
		removedTalents.add("EMPOWERING_SCROLLS");
	}

	private static final HashMap<String, String> renamedTalents = new HashMap<>();
	static{
		//v2.4.0
		renamedTalents.put("SECONDARY_CHARGE",          "VARIED_CHARGE");

		//v2.2.0
		renamedTalents.put("RESTORED_WILLPOWER",        "LIQUID_WILLPOWER");
		renamedTalents.put("ENERGIZING_UPGRADE",        "INSCRIBED_POWER");
		renamedTalents.put("MYSTICAL_UPGRADE",          "INSCRIBED_STEALTH");
		renamedTalents.put("RESTORED_NATURE",           "LIQUID_NATURE");
		renamedTalents.put("RESTORED_AGILITY",          "LIQUID_AGILITY");
		//v2.1.0
		renamedTalents.put("LIGHTWEIGHT_CHARGE",        "PRECISE_ASSAULT");
		//v2.0.0 BETA
		renamedTalents.put("LIGHTLY_ARMED",             "UNENCUMBERED_SPIRIT");
		//v2.0.0
		renamedTalents.put("ARMSMASTERS_INTUITION",     "VETERANS_INTUITION");
	}

	public static void restoreTalentsFromBundle( Bundle bundle, Hero hero ){
		if (bundle.contains("replacements")){
			Bundle replacements = bundle.getBundle("replacements");
			for (String key : replacements.getKeys()){
				String value = replacements.getString(key);
				if (renamedTalents.containsKey(key)) key = renamedTalents.get(key);
				if (renamedTalents.containsKey(value)) value = renamedTalents.get(value);
				if (!removedTalents.contains(key) && !removedTalents.contains(value)){
					try {
						hero.metamorphedTalents.put(Talent.valueOf(key), Talent.valueOf(value));
					} catch (Exception e) {
						ShatteredPixelDungeon.reportException(e);
					}
				}
			}
		}

		if (hero.heroClass != null)     initClassTalents(hero);
		if (hero.subClass != null)      initSubclassTalents(hero);
		if (hero.armorAbility != null)  initArmorTalents(hero);

		for (int i = 0; i < MAX_TALENT_TIERS; i++){
			LinkedHashMap<Talent, Integer> tier = hero.talents.get(i);
			Bundle tierBundle = bundle.contains(TALENT_TIER+(i+1)) ? bundle.getBundle(TALENT_TIER+(i+1)) : null;

			if (tierBundle != null){
				for (String tName : tierBundle.getKeys()){
					int points = tierBundle.getInt(tName);
					if (renamedTalents.containsKey(tName)) tName = renamedTalents.get(tName);
					if (!removedTalents.contains(tName)) {
						try {
							Talent talent = Talent.valueOf(tName);
							if (tier.containsKey(talent)) {
								tier.put(talent, Math.min(points, talent.maxPoints()));
							}
						} catch (Exception e) {
							ShatteredPixelDungeon.reportException(e);
						}
					}
				}
			}
		}
	}

}
