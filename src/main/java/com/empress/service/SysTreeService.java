package com.empress.service;

import com.empress.dao.SysAclMapper;
import com.empress.dao.SysAclModuleMapper;
import com.empress.dao.SysDeptMapper;
import com.empress.dto.AclDTO;
import com.empress.dto.AclModuleLevelDTO;
import com.empress.dto.DeptLevelDTO;
import com.empress.pojo.SysAcl;
import com.empress.pojo.SysAclModule;
import com.empress.pojo.SysDept;
import com.empress.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 层级树结构 Service
 *
 * @author Hystar
 * @date 2018/9/6
 */
@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysCoreService sysCoreService;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 获取当前用户所拥有的权限树
     *
     * @param userId
     * @return
     */
    public List<AclModuleLevelDTO> userAclTree(int userId) {
        // 获取当前用户对应的权限列表
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);

        // 生成权限列表
        List<AclDTO> aclDTOList = Lists.newArrayList();
        for (SysAcl sysAcl : userAclList) {
            AclDTO aclDTO = AclDTO.adapt(sysAcl);
            aclDTO.setChecked(true);
            aclDTO.setHasAcl(true);
            aclDTOList.add(aclDTO);
        }

        // 将权限列表转换成权限树
        return aclListToTree(aclDTOList);
    }

    /**
     * 获取角色所对应的权限树
     *
     * @param roleId
     * @return
     */
    public List<AclModuleLevelDTO> roleTree(int roleId) {
        // 1、获取当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // 2、获取当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        // 3、当前系统所有的权限点
        List<AclDTO> aclDTOList = Lists.newArrayList();

        // 用于存储 当前用户的权限ID列表
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        // 用于存储 角色已分配权限ID列表
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        // 获取所有的权限列表
        List<SysAcl> allAclList = sysAclMapper.getAll();
        for (SysAcl sysAcl : allAclList) {
            AclDTO aclDTO = AclDTO.adapt(sysAcl);
            if (userAclIdSet.contains(aclDTO.getId())) {
                aclDTO.setHasAcl(true);
            }
            if (roleAclIdSet.contains(aclDTO.getId())) {
                aclDTO.setChecked(true);
            }
            aclDTOList.add(aclDTO);
        }

        // 将权限列表转换成权限树
        return aclListToTree(aclDTOList);
    }

    /**
     * 将权限列表转换成权限树
     *
     * @param aclDTOList
     * @return
     */
    public List<AclModuleLevelDTO> aclListToTree(List<AclDTO> aclDTOList) {
        if (CollectionUtils.isEmpty(aclDTOList)) {
            return Lists.newArrayList();
        }

        // 获取权限模块树
        List<AclModuleLevelDTO> aclModuleLevelDTOList = aclModuleTree();

        // map中存放的格式： aclModuleId -> [aclDTO1, aclDTO2, ...]，相当于new Map<Integer, List<Object>>
        Multimap<Integer, AclDTO> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDTO aclDTO : aclDTOList) {
            if (aclDTO.getStatus() == 1) {
                moduleIdAclMap.put(aclDTO.getAclModuleId(), aclDTO);
            }
        }

        // 将权限点绑定到权限模块树中
        bindAclsWithOrder(aclModuleLevelDTOList, moduleIdAclMap);
        return aclModuleLevelDTOList;
    }

    /**
     * 将权限点绑定到权限模块树中
     */
    public void bindAclsWithOrder(List<AclModuleLevelDTO> aclModuleLevelDTOList, Multimap<Integer, AclDTO> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelDTOList)) {
            return;
        }
        // 遍历当前层级，获取每一个模块
        for (AclModuleLevelDTO aclModuleLevelDTO : aclModuleLevelDTOList) {
            // 获取当前模块下的所有权限点
            List<AclDTO> aclDTOList = (List<AclDTO>) moduleIdAclMap.get(aclModuleLevelDTO.getId());
            if (CollectionUtils.isNotEmpty(aclDTOList)) {
                Collections.sort(aclDTOList, aclSeqComparator);
                aclModuleLevelDTO.setAclDTOList(aclDTOList);
            }
            bindAclsWithOrder(aclModuleLevelDTO.getAclModuleLevelDTOList(), moduleIdAclMap);
        }
    }

    /**
     * 将权限点按照seq进行排序
     */
    public Comparator<AclDTO> aclSeqComparator = new Comparator<AclDTO>() {
        @Override
        public int compare(AclDTO o1, AclDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 获取部门树
     *
     * @return
     */
    public List<DeptLevelDTO> deptTree() {
        // 获取所有部门记录
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDTO> deptLevelDTOList = Lists.newArrayList();
        for (SysDept sysDept : deptList) {
            DeptLevelDTO deptLevelDTO = DeptLevelDTO.adapt(sysDept);
            deptLevelDTOList.add(deptLevelDTO);
        }
        return deptListToTree(deptLevelDTOList);
    }

    /**
     * 将部门列表转换成部门树
     * 这个方法处理ROOT层级部门树，ROOT层级下的部门使用递归进行处理
     *
     * @param deptLevelDTOList
     * @return
     */
    public List<DeptLevelDTO> deptListToTree(List<DeptLevelDTO> deptLevelDTOList) {
        if (CollectionUtils.isEmpty(deptLevelDTOList)) {
            return Lists.newArrayList();
        }

        // map中存放的格式： level -> [dept1, dept2, ...]，相当于new Map<String, List<Object>>
        Multimap<String, DeptLevelDTO> levelDTOMultiMap = ArrayListMultimap.create();
        List<DeptLevelDTO> rootList = Lists.newArrayList();
        for (DeptLevelDTO deptLevelDTO : deptLevelDTOList) {
            levelDTOMultiMap.put(deptLevelDTO.getLevel(), deptLevelDTO);
            // 如果为顶级部门，则添加到rootList中
            if (LevelUtil.ROOT.equals(deptLevelDTO.getLevel())) {
                rootList.add(deptLevelDTO);
            }
        }

        // 按照seq从小到大进行排序
        Collections.sort(rootList, deptLevelDTOComparator);

        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDTOMultiMap);
        return rootList;
    }

    /**
     * 递归生成部门层级树
     *
     * @param deptLevelDTOList 部门层级树列表
     * @param level            当前部门层级
     * @param levelDTOMultiMap
     */
    public void transformDeptTree(List<DeptLevelDTO> deptLevelDTOList, String level, Multimap<String, DeptLevelDTO> levelDTOMultiMap) {
        for (int i = 0; i < deptLevelDTOList.size(); i++) {
            // 遍历该层的每个元素
            DeptLevelDTO deptLevelDTO = deptLevelDTOList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDTO.getId());
            // 处理下一层
            List<DeptLevelDTO> tempDeptList = (List<DeptLevelDTO>) levelDTOMultiMap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptLevelDTOComparator);
                // 设置下一层部门
                deptLevelDTO.setDeptLevelDTOList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDTOMultiMap);
            }
        }
    }

    /**
     * 通过seq将list集合中的对象进行排序
     */
    public Comparator<DeptLevelDTO> deptLevelDTOComparator = new Comparator<DeptLevelDTO>() {
        @Override
        public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 获取权限模块树
     *
     * @return
     */
    public List<AclModuleLevelDTO> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();

        List<AclModuleLevelDTO> aclModuleLevelDTOList = Lists.newArrayList();
        for (SysAclModule sysAclModule : aclModuleList) {
            AclModuleLevelDTO aclModuleLevelDTO = AclModuleLevelDTO.adapt(sysAclModule);
            aclModuleLevelDTOList.add(aclModuleLevelDTO);
        }
        return aclModuleListToTree(aclModuleLevelDTOList);
    }

    /**
     * 将权限模块列表转换成权限模块树
     * 这个方法处理ROOT层级权限模块树，ROOT层级下的权限模块使用递归进行处理
     *
     * @param aclModuleLevelDTOList
     * @return
     */
    public List<AclModuleLevelDTO> aclModuleListToTree(List<AclModuleLevelDTO> aclModuleLevelDTOList) {
        if (CollectionUtils.isEmpty(aclModuleLevelDTOList)) {
            return Lists.newArrayList();
        }

        // map中存放的格式： level -> [aclModule1, aclModule2, ...]，相当于new Map<String, List<Object>>
        Multimap<String, AclModuleLevelDTO> levelDTOMultiMap = ArrayListMultimap.create();
        List<AclModuleLevelDTO> rootList = Lists.newArrayList();
        for (AclModuleLevelDTO aclModuleLevelDTO : aclModuleLevelDTOList) {
            levelDTOMultiMap.put(aclModuleLevelDTO.getLevel(), aclModuleLevelDTO);
            // 如果为顶级权限模块，则添加到rootList中
            if (LevelUtil.ROOT.equals(aclModuleLevelDTO.getLevel())) {
                rootList.add(aclModuleLevelDTO);
            }
        }

        // 按照seq从小到大进行排序
        Collections.sort(rootList, aclModuleLevelDTOComparator);

        // 递归生成树
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelDTOMultiMap);
        return rootList;
    }

    /**
     * 递归生成权限模块层级树
     *
     * @param aclModuleLevelDTOList 权限模块层级树列表
     * @param level                 当前权限模块层级
     * @param levelDTOMultiMap
     */
    public void transformAclModuleTree(List<AclModuleLevelDTO> aclModuleLevelDTOList, String level, Multimap<String, AclModuleLevelDTO> levelDTOMultiMap) {
        for (int i = 0; i < aclModuleLevelDTOList.size(); i++) {
            // 遍历该层的每个元素
            AclModuleLevelDTO aclModuleLevelDTO = aclModuleLevelDTOList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, aclModuleLevelDTO.getId());
            // 处理下一层
            List<AclModuleLevelDTO> tempAclModuleList = (List<AclModuleLevelDTO>) levelDTOMultiMap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempAclModuleList)) {
                // 排序
                Collections.sort(tempAclModuleList, aclModuleLevelDTOComparator);
                // 设置下一层权限模块
                aclModuleLevelDTO.setAclModuleLevelDTOList(tempAclModuleList);
                // 进入到下一层处理
                transformAclModuleTree(tempAclModuleList, nextLevel, levelDTOMultiMap);
            }
        }
    }

    /**
     * 通过seq将list集合中的对象进行排序
     */
    public Comparator<AclModuleLevelDTO> aclModuleLevelDTOComparator = new Comparator<AclModuleLevelDTO>() {
        @Override
        public int compare(AclModuleLevelDTO o1, AclModuleLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };






}
