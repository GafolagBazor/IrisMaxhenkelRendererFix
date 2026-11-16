# Iris Maxhenkel Renderer Fix

A lightweight client-side fix for **Minecraft 26.3 (NeoForge)** that resolves severely stretched, exploded, and corrupted geometry on entities and custom models rendered by **Max Henkel's mods** (such as **Ultimate Car Mod**, **Ultimate Plane Mod**, and other mods using `corelib` triangle pipelines) when using **Iris Shaders**.

## What's Fixed
- **Custom Triangle Pipelines:** Maps `entity_cutout_triangles` and `entity_translucent_triangles` pipelines to Iris's extended entity shader programs (`ENTITIES_CUTOUT_DIFFUSE`, `ENTITIES_TRANSLUCENT`, and `SHADOW_ENTITIES_CUTOUT`), preventing vertex stride mismatches.
- **Triangle Tangent Calculation:** Safely zeroes out tangent computation on non-quad `TRIANGLES` meshes in `NormalHelper` so vertex attributes are no longer corrupted.

---

## Before & After

| Before (Iris 1.11.7 without fix) | After (With IrisMaxhenkelRendererFix) |
| :---: | :---: |
| <img width="1920" height="1080" alt="Minecraft Screenshot 2026 11 16 - 22 02 30 28" src="https://github.com/user-attachments/assets/646236c0-5f53-46d2-a895-f2a45c7acb4d" /> | <img width="1920" height="1080" alt="Minecraft Screenshot 2026 11 16 - 22 03 53 80" src="https://github.com/user-attachments/assets/3024e532-a5a1-491b-bf8d-c5bdcfdd64ef" /> |

### Test Environment

| Component | Version / Details |
| :--- | :--- |
| **Minecraft** | `26.3` |
| **Mod Loader** | `NeoForge 26.3.0.76` |
| **Iris Shaders** | `1.11.7+mc26.3` |
| **Sodium** | `0.9.3-beta.1+mc26.3` |
| **Tested Mod** | `Ultimate Car Mod 1.0.51+26.3` |
| **Shaderpack** | `Rethinking Voxels r0.1-beta9` |
| **GPU** | `NVIDIA GeForce RTX 5060 Ti Infinity 3` |
| **Mod Version** | `1.0.0_61518_1.11.7+mc26.3` |

---

## Installation
1. Download the latest `.jar` file from the [**Releases**](https://github.com/GafolagBazor/IrisMaxhenkelRendererFix/releases/latest) page.
2. Place it into your `.minecraft/mods` folder alongside **Iris Shaders** and your Max Henkel mods.

## Requirements & Compatibility
- **Minecraft:** `26.3`
- **Mod Loader:** `NeoForge 26.3.0.76+`
- **Side:** Client-side only (safe to use when connecting to unmodified servers)

## License
This project is licensed under the **Apache License 2.0** — see the [LICENSE](LICENSE) file for details.
